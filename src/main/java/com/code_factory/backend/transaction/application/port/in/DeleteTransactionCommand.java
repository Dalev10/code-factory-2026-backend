package com.code_factory.backend.transaction.application.port.in;

import java.util.UUID;

public record DeleteTransactionCommand(UUID transactionId, UUID userId) {}
