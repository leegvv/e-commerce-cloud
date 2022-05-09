package net.arver.commerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.BalanceInfo;
import net.arver.commerce.service.BalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BalanceController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(final BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("current-balance")
    public BalanceInfo getCurrentUserBalanceInfo() {
        return balanceService.getCurrentUserBalanceInfo();
    }

    @PutMapping("deduct-balance")
    public BalanceInfo deductBalance(@RequestBody final BalanceInfo balanceInfo) {
        return balanceService.deductBalance(balanceInfo);
    }



}
