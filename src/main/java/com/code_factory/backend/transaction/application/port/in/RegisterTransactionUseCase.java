package com.code_factory.backend.transaction.application.port.in;
import com.code_factory.backend.transaction.application.port.in.RegisterExpenseCommand;

import com.code_factory.backend.transaction.domain.model.Transaction;


public interface RegisterTransactionUseCase {
    Transaction registerIncome(RegisterIncomeCommand command);
    Transaction registerExpense(RegisterExpenseCommand command);
}
