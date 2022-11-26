package net.arver.commerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.annotation.IgnoreResponseAdvice;
import net.arver.commerce.service.JWTService;
import net.arver.commerce.util.JsonUtil;
import net.arver.commerce.vo.JwtToken;
import net.arver.commerce.vo.UsernameAndPassword;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外暴露的授权服务接口.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    /**
     * jwt服务.
     */
    private final JWTService jwtService;

    public AuthorityController(final JWTService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * 从授权中心获取 Token (登陆认证).
     * @param usernameAndPassword 用户信息
     * @return token
     * @throws Exception 异常
     */
    @IgnoreResponseAdvice
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("request to get token with param: [{}]", JsonUtil.toJson(usernameAndPassword));
        return new JwtToken(jwtService.generateToken(usernameAndPassword.getUsername(), usernameAndPassword.getPassword()));
    }

    /**
     * 注册并返回当前注册用户的 Token.
     * @param usernameAndPassword 用户信息
     * @return token
     * @throws Exception 异常
     */
    @IgnoreResponseAdvice
    @PostMapping("/register")
    public JwtToken register(@RequestBody final UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("register user with param: [{}]", JsonUtil.toJson(usernameAndPassword));
        return new JwtToken(jwtService.registerUserAndGenerateToken(usernameAndPassword));
    }
}
