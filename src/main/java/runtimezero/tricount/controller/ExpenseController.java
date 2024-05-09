package runtimezero.tricount.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import runtimezero.tricount.dto.ExpenseRequest;
import runtimezero.tricount.dto.ExpenseResult;
import runtimezero.tricount.dto.ApiResponse;
import runtimezero.tricount.service.ExpenseService;


@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    // 정산 추가
    @PostMapping("/expenses/add")
    public ApiResponse<ExpenseResult> addExpenseToSettlement(
            @Valid @RequestBody ExpenseRequest expenseRequest
    ) {
        return new ApiResponse<ExpenseResult>().ok(expenseService.addExpense(expenseRequest));
    }
}
