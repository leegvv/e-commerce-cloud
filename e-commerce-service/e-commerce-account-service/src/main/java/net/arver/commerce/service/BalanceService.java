package net.arver.commerce.service;

import net.arver.commerce.account.BalanceInfo;

/**
 * 余额相关接口定义.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface BalanceService {

    /**
     * 获取当前用户余额信息.
     * @return
     */
    BalanceInfo getCurrentUserBalanceInfo();

    /**
     * 扣减用户余额
     * @param balanceInfo 代表想要扣减的余额
     * @return
     */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
