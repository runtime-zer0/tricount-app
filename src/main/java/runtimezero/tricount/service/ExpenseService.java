package runtimezero.tricount.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runtimezero.tricount.dto.ExpenseRequest;
import runtimezero.tricount.dto.ExpenseResult;
import runtimezero.tricount.enums.TricountErrorCode;
import runtimezero.tricount.entity.Expense;
import runtimezero.tricount.entity.Member;
import runtimezero.tricount.entity.Settlement;
import runtimezero.tricount.repository.ExpenseRepository;
import runtimezero.tricount.repository.MemberRepository;
import runtimezero.tricount.repository.SettlementRepository;
import runtimezero.tricount.util.TricountApiException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final SettlementRepository settlementRepository;

    @Transactional
    public ExpenseResult addExpense(ExpenseRequest expenseRequest) {
        // 예외 처리
        Optional<Member> payer = memberRepository.findById(expenseRequest.getPayerMemberId());
        if (!payer.isPresent()) {
            throw new TricountApiException("INVALID MEMBER ID! (Payer)", TricountErrorCode.INVALID_INPUT_VALUE);
        }

        Optional<Settlement> settlement = settlementRepository.findById(expenseRequest.getSettlementId());
        if (!settlement.isPresent()) {
            throw new TricountApiException("INVALID SETTLEMENT ID", TricountErrorCode.INVALID_INPUT_VALUE);
        }
        Expense expense = Expense.builder()
                .name(expenseRequest.getName())
                .settlementId(expenseRequest.getSettlementId())
                .payerMemberId(expenseRequest.getPayerMemberId())
                .amount(expenseRequest.getAmount())
                .expenseDateTime(Objects.nonNull(expenseRequest.getExpenseDateTime()) ? expenseRequest.getExpenseDateTime() : LocalDateTime.now())
                .build();
        expenseRepository.save(expense);

        return new ExpenseResult(expense, payer.get());
    }
}
