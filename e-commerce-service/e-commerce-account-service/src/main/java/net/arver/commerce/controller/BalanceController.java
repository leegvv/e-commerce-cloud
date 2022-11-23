package net.arver.commerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.BalanceInfo;
import net.arver.commerce.service.BalanceService;
import org.springframework.web.bind.annotation.*;

/**
 * BalanceController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Tag(name = "用户账户", description = "BalanceController")
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(final BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Operation(summary = "当前余额")
    @GetMapping("current-balance")
    public BalanceInfo getCurrentUserBalanceInfo() {
        return balanceService.getCurrentUserBalanceInfo();
    }

    @Operation(summary = "扣减余额")
    @PutMapping("deduct-balance")
    public BalanceInfo deductBalance(@RequestBody final BalanceInfo balanceInfo) {
        return balanceService.deductBalance(balanceInfo);
    }

}
