package net.arver.commerce.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户余额信息.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfo {

    /**
     *  用户主键 id.
     */
    private Long userId;

    /**
     *  用户账户余额.
     */
    private Long balance;
}
