package com.code_factory.backend.transaction.application.port.in;

import com.code_factory.backend.transaction.domain.model.Transaction;

public interface UpdateTransactionCategoryUseCase {

    Transaction execute(UpdateTransactionCategoryCommand command);

}
