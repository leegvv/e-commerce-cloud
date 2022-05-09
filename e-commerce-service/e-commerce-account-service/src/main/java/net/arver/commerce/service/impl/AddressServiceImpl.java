package net.arver.commerce.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.AddressInfo;
import net.arver.commerce.account.TableId;
import net.arver.commerce.dao.AddressDao;
import net.arver.commerce.entity.Address;
import net.arver.commerce.exception.ServiceException;
import net.arver.commerce.filter.LoginUserInfoHolder;
import net.arver.commerce.service.AddressService;
import net.arver.commerce.util.JsonUtil;
import net.arver.commerce.vo.LoginUserInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AddressServiceImpl.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    public AddressServiceImpl(final AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public TableId createAddressInfo(final AddressInfo addressInfo) {
        final LoginUserInfo loginUserInfo = LoginUserInfoHolder.get();
        final List<Address> addresses = addressInfo.getAddressItems()
                .stream().map(item -> Address.to(loginUserInfo.getId(), item)).collect(Collectors.toList());

        final List<Address> savedRecords = addressDao.saveAll(addresses);
        final List<Long> ids = savedRecords.stream().map(Address::getId).collect(Collectors.toList());
        log.info("create address info: [{}], [{}]", loginUserInfo.getId(), JsonUtil.toJson(ids));

        return new TableId(ids.stream().map(TableId.Id::new).collect(Collectors.toList()));
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {
        final LoginUserInfo loginUserInfo = LoginUserInfoHolder.get();
        final List<Address> addresses = addressDao.findAllByUserId(loginUserInfo.getId());
        final List<AddressInfo.AddressItem> addressItems = addresses.stream()
                .map(Address::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(), addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(final Long id) {
        final Address address = addressDao.findById(id).orElse(null);
        if (address == null) {
            throw new ServiceException("地址不存在");
        }
        return new AddressInfo(address.getUserId(), Collections.singletonList(address.toAddressItem()));
    }

    @Override
    public AddressInfo getAddressInfoByTableId(final TableId tableId) {
        final List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());

        final List<Address> addresses = addressDao.findAllById(ids);
        if (CollectionUtils.isEmpty(addresses)) {
            return new AddressInfo(-1L, Collections.emptyList());
        }
        final List<AddressInfo.AddressItem> addressItems = addresses.stream()
                .map(Address::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(addresses.get(0).getUserId(), addressItems);
    }
}
