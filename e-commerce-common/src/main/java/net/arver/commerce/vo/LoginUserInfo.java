package net.arver.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginUserInfo.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {

    /**
     * 用户id.
     */
    private Long id;

    /**
     * 用户名.
     */
    private String username;
}
