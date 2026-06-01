package com.code_factory.backend.identity.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "active_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String jti;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private LocalDateTime lastActivityAt;

    @Column(nullable = false, columnDefinition = "boolean not null default false")
    @Builder.Default
    private boolean revoked = false;
}
