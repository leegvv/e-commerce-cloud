package net.arver.commerce.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import net.arver.commerce.constant.CommonConstant;
import net.arver.commerce.vo.LoginUserInfo;

/**
 * JWT Token 解析工具类.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public final class TokenUtil {

    /**
     * 构造函数.
     */
    private TokenUtil() {}

    /**
     * 从 JWT Token 中解析 LoginUserInfo 对象.
     * @param token token
     * @return 用户信息
     * @throws Exception 异常
     */
    public static LoginUserInfo parseUserInfoFromToken(final String token) throws Exception {
        if (token == null) {
            return null;
        }

        final Jws<Claims> claimsJws = parseToken(token, getPublicKey());
        final Claims body = claimsJws.getBody();

        // 如果 Token 已经过期， 返回null
        if (body.getExpiration().before(Calendar.getInstance().getTime())) {
            return null;
        }

        // 返回 Token 中保存的用户信息
        return JsonUtil.parse(body.get(CommonConstant.JWT_USER_INFO_KEY).toString(), LoginUserInfo.class);
    }

    /**
     * 通过公钥去解析 JWT Token.
     * @param token token
     * @param publicKey publicKey
     * @return jwt 属性
     */
    private static Jws<Claims> parseToken(final String token, final PublicKey publicKey) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }

    /**
     * 根据本地存储的公钥获取 PublicKey 对象.
     * @return 公钥
     * @throws Exception 异常
     */
    private static PublicKey getPublicKey() throws Exception {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(CommonConstant.PUBLIC_KEY));
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

}
