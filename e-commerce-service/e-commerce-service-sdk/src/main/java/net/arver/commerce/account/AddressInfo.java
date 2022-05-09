package net.arver.commerce.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
public class AddressInfo {

    /**
     * 地址所属用户 id.
     */
    private Long userId;

    /**
     * 地址详细信息.
     */
    private List<AddressItem> addressItems;

    /**
     * 用户的单个地址信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressItem {

        public AddressItem(final Long id) {
            this.id = id;
        }

        /**
         * 地址表主键 id.
         */
        private Long id;

        /**
         * 用户姓名.
         */
        private String username;

        /**
         * 电话号码.
         */
        private String phone;

        /**
         * 省.
         */
        private String province;

        /**
         * 市.
         */
        private String city;

        /**
         * 详细的地址.
         */
        private String addressDetail;

        /**
         * 创建时间.
         */
        private Date createTime;

        /**
         * 更新时间.
         */
        private Date updateTime;

        public UserAddress toUserAddress() {
            final UserAddress userAddress = new UserAddress();
            userAddress.setUsername(this.username);
            userAddress.setPhone(this.phone);
            userAddress.setProvince(this.province);
            userAddress.setCity(this.city);
            userAddress.setAddressDetail(this.addressDetail);
            return userAddress;
        }

    }

}
