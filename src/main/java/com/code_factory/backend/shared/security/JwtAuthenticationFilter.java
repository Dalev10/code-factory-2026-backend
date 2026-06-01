package com.code_factory.backend.shared.security;

import com.code_factory.backend.identity.application.port.out.JwtTokenPort;
import com.code_factory.backend.identity.application.port.out.SessionRepositoryPort;
import com.code_factory.backend.identity.application.port.out.dto.SessionInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final int INACTIVITY_TIMEOUT_MINUTES = 2;

    private final JwtTokenPort jwtTokenPort;
    private final UserDetailsService userDetailsService;
    private final SessionRepositoryPort sessionRepositoryPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtTokenPort.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jti = jwtTokenPort.extractJti(token);
        Optional<SessionInfo> sessionOpt = sessionRepositoryPort.findByJti(jti);

        if (sessionOpt.isEmpty() || sessionOpt.get().revoked()) {
            sessionOpt.ifPresent(s ->
                log.warn("Intento de uso de token revocado. Email: {}, JTI: {}", s.userEmail(), jti)
            );
            sendUnauthorized(response, "Sesión no válida o expirada");
            return;
        }

        SessionInfo session = sessionOpt.get();

        if (session.lastActivityAt().isBefore(LocalDateTime.now().minusMinutes(INACTIVITY_TIMEOUT_MINUTES))) {
            sessionRepositoryPort.revokeSession(jti);
            sendUnauthorized(response, "Sesión expirada por inactividad");
            return;
        }

        sessionRepositoryPort.updateLastActivity(jti);

        String email = jwtTokenPort.extractEmail(token);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\":\"" + message + "\",\"status\":401}");
    }
}
