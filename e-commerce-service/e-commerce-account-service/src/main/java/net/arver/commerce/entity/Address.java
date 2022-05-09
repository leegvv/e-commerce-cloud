package net.arver.commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.arver.commerce.account.AddressInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Address.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_address", schema = "commerce")
@EntityListeners(AuditingEntityListener.class)
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "province")
    private String province;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "address_detail")
    private String addressDetail;

    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Timestamp updateTime;

    /**
     * 将 AddressInfo 转换为 Address
     * @param userId
     * @param addressItem
     * @return
     */
    public static Address to(final Long userId, final AddressInfo.AddressItem addressItem) {
        final Address address = new Address();
        BeanUtils.copyProperties(addressItem, address);
        address.setUserId(userId);
        return address;
    }

    public AddressInfo.AddressItem toAddressItem() {
        final AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        BeanUtils.copyProperties(this, addressItem);
        return addressItem;
    }
}
