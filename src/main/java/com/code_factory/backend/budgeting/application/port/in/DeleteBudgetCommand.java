package com.code_factory.backend.budgeting.application.port.in;

import java.util.UUID;  

public record DeleteBudgetCommand (UUID id, UUID userId) {}
