package net.arver.commerce.dao;

import java.util.List;
import net.arver.commerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

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
     * @param userId 用户id
     * @return 地址
     */
    List<Address> findAllByUserId(Long userId);
}
