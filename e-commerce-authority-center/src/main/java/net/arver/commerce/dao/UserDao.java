package net.arver.commerce.dao;

import net.arver.commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserDao.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询对象.
     * @param username 用户名
     * @return {@link User}
     */
    User findByUsername(String username);

    /**
     * 根据用户名和密码查询对象
     * @param username 用户名
     * @param password 密码
     * @return {@link User}
     */
    User findByUsernameAndPassword(String username, String password);

}
