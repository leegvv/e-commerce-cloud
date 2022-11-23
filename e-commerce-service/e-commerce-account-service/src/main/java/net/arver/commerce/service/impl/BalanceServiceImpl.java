package net.arver.commerce.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.BalanceInfo;
import net.arver.commerce.dao.BalanceDao;
import net.arver.commerce.entity.Balance;
import net.arver.commerce.exception.ServiceException;
import net.arver.commerce.filter.AccessContext;
import net.arver.commerce.service.BalanceService;
import net.arver.commerce.vo.LoginUserInfo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * BalanceServiceImpl.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
public class BalanceServiceImpl implements BalanceService {
    private BalanceDao balanceDao;
    public BalanceServiceImpl(final BalanceDao balanceDao) {
        this.balanceDao = balanceDao;
    }

    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        final LoginUserInfo loginUserInfo = AccessContext.get();
        final BalanceInfo balanceInfo = new BalanceInfo(loginUserInfo.getId(), 0L);

        final Balance balance = balanceDao.findByUserId(loginUserInfo.getId());
        if (balance == null) {
            final Balance newBalance = new Balance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            balanceDao.save(newBalance);
            log.info("init user balance record: [{}]", newBalance.getId());
        } else {
            balanceInfo.setBalance(balance.getBalance());
        }
        return balanceInfo;
    }

    @Override
    @Transactional
    public BalanceInfo deductBalance(final BalanceInfo balanceInfo) {
        final LoginUserInfo loginUserInfo = AccessContext.get();

        // 判断扣减余额是否小于当前余额
        final Balance balance = balanceDao.findByUserId(loginUserInfo.getId());
        if (balance == null || balance.getBalance() - balanceInfo.getBalance() < 0) {
            throw new ServiceException("余额不足");
        }

        final Long sourceBalance = balance.getBalance();
        balance.setBalance(sourceBalance - balanceInfo.getBalance());
        balanceDao.save(balance);
        log.info("deduct balance: [{}], [{}], [{}]", balance.getId(), sourceBalance, balanceInfo.getBalance());

        return new BalanceInfo(balance.getId(), balance.getBalance());
    }
}
