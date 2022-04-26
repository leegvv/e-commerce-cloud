package net.arver.commerce.service;

import net.arver.commerce.vo.UsernameAndPassword;

/**
 * JWT 相关服务接口定义.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface JWTService {

    /**
     * 生成 JWT Token,使用默认的超时时间
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String generateToken(String username, String password) throws Exception;

    /**
     * 生成 JWT Token,使用指定的超时时间
     * @param username 用户名
     * @param password 密码
     * @param expire 超时时间
     * @return token
     */
    String generateToken(String username, String password, int expire) throws Exception;

    /**
     * 注册用户并生成 Token 返回
     * @param usernameAndPassword
     * @return
     */
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
