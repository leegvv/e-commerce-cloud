package net.arver.commerce.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import net.arver.commerce.constant.CommonConstant;
import net.arver.commerce.vo.LoginUserInfo;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;

/**
 * JWT Token 解析工具类.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class TokenUtil {

    /**
     * 从 JWT Token 中解析 LoginUserInfo 对象.
     * @param token
     * @return
     * @throws Exception
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
     * @param token
     * @param publicKey
     * @return
     */
    private static Jws<Claims> parseToken(final String token, final PublicKey publicKey) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
    }

    /**
     * 根据本地存储的公钥获取 PublicKey 对象.
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey() throws Exception {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(CommonConstant.PUBLIC_KEY));
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

}
