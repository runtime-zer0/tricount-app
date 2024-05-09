package runtimezero.tricount.repository;


import runtimezero.tricount.dto.ExpenseResult;
import runtimezero.tricount.entity.Expense;

import java.util.List;

public interface ExpenseRepository {
    Expense save(Expense expense);

    List<ExpenseResult> findExpenseWithMemberBySettlementId(Long settlementId);
}
