package net.arver.commerce.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户地址信息.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    /**
     *  用户姓名.
     */
    private String username;

    /**
     *  电话号码.
     */
    private String phone;

    /**
     *  省.
     */
    private String province;

    /**
     *  市.
     */
    private String city;

    /**
     *  详细的地址.
     */
    private String addressDetail;
}
