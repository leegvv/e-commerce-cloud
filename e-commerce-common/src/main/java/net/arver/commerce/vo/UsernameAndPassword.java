package net.arver.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UsernameAndPassword.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPassword {

    /**
     * 用户名.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;

}
