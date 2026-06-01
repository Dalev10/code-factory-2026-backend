package com.code_factory.backend.identity.application.port.out.dto;

import java.time.LocalDateTime;

public record SessionInfo(String jti, String userEmail, LocalDateTime lastActivityAt, boolean revoked) {}
