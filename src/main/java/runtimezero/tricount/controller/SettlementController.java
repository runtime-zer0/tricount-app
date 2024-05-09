package runtimezero.tricount.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import runtimezero.tricount.dto.ApiResponse;
import runtimezero.tricount.dto.BalanceResult;
import runtimezero.tricount.entity.Settlement;
import runtimezero.tricount.service.SettlementService;
import runtimezero.tricount.util.MemberContext;

@RestController
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    @PostMapping("/settles/create")
    public ApiResponse<Settlement> createSettlement(@RequestParam String settlementName) {
        return new ApiResponse<Settlement>().ok(settlementService.createAmdJoinSettlement(settlementName, MemberContext.getCurrentMember()));
    }

    @PostMapping("/settles/{id}/join")
    public ApiResponse joinSettlement(@PathVariable("id") Long settlementId) {
        settlementService.joinSettlement(settlementId, MemberContext.getCurrentMember().getId());
        return new ApiResponse<>().ok();
    }

    @GetMapping("/settles/{id}/balance")
    public ApiResponse<BalanceResult> getSettlementBalanceResult(@PathVariable("id") Long settlementId) {
        return new ApiResponse<BalanceResult>().ok(settlementService.getBalanceResult(settlementId));
    }
}
