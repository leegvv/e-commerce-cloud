package net.arver.commerce;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.dao.UserDao;
import net.arver.commerce.entity.User;
import net.arver.commerce.util.JsonUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * UserTest.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@SpringBootTest
public class UserTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void createUserRecord() {
        final User user = new User();
        user.setUsername("arver2");
        user.setPassword(Md5Crypt.apr1Crypt("12345678"));
        user.setExtraInfo("{}");
        userDao.save(user);
        log.info("save user: [{}]", JsonUtil.toJson(user));
    }

}
