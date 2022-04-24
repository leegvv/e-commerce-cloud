package net.arver.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权中心鉴权后给客户端的token.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    /**
     * JWT.
     */
    private String token;

}
