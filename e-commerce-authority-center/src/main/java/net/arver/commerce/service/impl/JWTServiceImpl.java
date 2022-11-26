package net.arver.commerce.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.constant.AuthorityConstant;
import net.arver.commerce.constant.CommonConstant;
import net.arver.commerce.dao.UserDao;
import net.arver.commerce.entity.User;
import net.arver.commerce.service.JWTService;
import net.arver.commerce.util.JsonUtil;
import net.arver.commerce.vo.LoginUserInfo;
import net.arver.commerce.vo.UsernameAndPassword;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JWT 相关服务接口实现.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JWTServiceImpl implements JWTService {

    private final UserDao userDao;

    public JWTServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String generateToken(final String username, final String password) throws Exception {
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(final String username, final String password, final int expire) throws Exception {
        int validExpire = expire;
        // 验证用户是否能够通过授权验证，即输入的用户名和密码能否匹配数据表数据
        final User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            log.error("can not find user: [{}], [{}]", username, password);
            return null;
        }

        // Token中塞入对象，即JWT中存储的信息，后端拿到这些信息就可以指导是哪个用户在操作
        final LoginUserInfo loginUserInfo = new LoginUserInfo(user.getId(), user.getUsername());

        if (expire <= 0) {
            validExpire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        // 计算超时时间
        final ZonedDateTime zdt = LocalDateTime.now().plus(validExpire, ChronoUnit.DAYS).atZone(ZoneId.systemDefault());
        final Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                // jwt paload --> KV
                .claim(CommonConstant.JWT_USER_INFO_KEY, JsonUtil.toJson(loginUserInfo))
                // jwt id
                .setId(UUID.randomUUID().toString())
                // jwt 过期时间
                .setExpiration(expireDate)
                // jwt 签名 --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();

    }

    @Override
    public String registerUserAndGenerateToken(final UsernameAndPassword usernameAndPassword) throws Exception {
        // 校验用户名是否存在
        final User oldUser = userDao.findByUsername(usernameAndPassword.getUsername());
        if (oldUser != null) {
            log.error("username is registered: [{}]", oldUser.getUsername());
            return null;
        }
        User user = new User();
        user.setUsername(usernameAndPassword.getUsername());
        user.setPassword(usernameAndPassword.getPassword());
        user.setExtraInfo("{}");

        user = userDao.save(user);
        log.info("register user success: [{}], [{}]", user.getUsername(), user.getId());

        return generateToken(user.getUsername(), user.getPassword());
    }

    /**
     * 根据本地存储的私钥获取到 PrivateKey 对象.
     * @return 私钥
     * @throws Exception 异常
     */
    private PrivateKey getPrivateKey() throws Exception {
        final PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(AuthorityConstant.PRIVATE_KEY)
        );
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }
}
