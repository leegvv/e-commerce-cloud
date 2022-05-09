package net.arver.commerce.dao;

import net.arver.commerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AddressDao.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface AddressDao extends JpaRepository<Address, Long> {

    /**
     * 根据用户 id 查询所有的地址信息.
     * @param userId
     * @return
     */
    List<Address> findAllByUserId(Long userId);
}
