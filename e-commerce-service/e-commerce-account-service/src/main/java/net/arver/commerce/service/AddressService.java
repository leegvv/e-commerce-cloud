package net.arver.commerce.service;

import net.arver.commerce.account.AddressInfo;
import net.arver.commerce.account.TableId;

/**
 * AddressService.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public interface AddressService {

    /**
     * 创建用户自制信息.
     * @param addressInfo
     * @return
     */
    TableId createAddressInfo(AddressInfo addressInfo);

    /**
     * 获取当前登陆用户的地址信息.
     * @return
     */
    AddressInfo getCurrentAddressInfo();

    /**
     * 通过 id 获取用户地址信息.
     * @param id
     * @return
     */
    AddressInfo getAddressInfoById(Long id);

    /**
     * 通过 TableId 获取用户地址信息.
     * @param tableId
     * @return
     */
    AddressInfo getAddressInfoByTableId(TableId tableId);
}
