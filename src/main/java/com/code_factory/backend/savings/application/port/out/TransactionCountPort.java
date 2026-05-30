package com.code_factory.backend.savings.application.port.out;

import java.util.UUID;

public interface TransactionCountPort {
    long countByUserId(UUID userId);
}
