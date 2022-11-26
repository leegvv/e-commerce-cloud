package net.arver.commerce.dao;

import net.arver.commerce.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BalanceDao.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface BalanceDao extends JpaRepository<Balance, Long> {

    /**
     * 根据用户 id 查询账户余额.
     * @param userId 用户id
     * @return 金额
     */
    Balance findByUserId(Long userId);
}
