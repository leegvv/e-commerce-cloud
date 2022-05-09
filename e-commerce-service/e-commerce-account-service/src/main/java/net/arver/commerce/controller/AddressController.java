package net.arver.commerce.controller;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.AddressInfo;
import net.arver.commerce.account.TableId;
import net.arver.commerce.entity.Address;
import net.arver.commerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AddressController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@RequestMapping("/address")
@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(final AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create-address")
    public TableId createAddressInfo(@RequestBody final AddressInfo addressInfo) {
        return addressService.createAddressInfo(addressInfo);
    }

    @GetMapping("/current-address")
    public AddressInfo getCurrentAddressInfo() {
        return addressService.getCurrentAddressInfo();
    }

    @GetMapping("/address-info")
    public AddressInfo getAddressInfo(@RequestParam final Long id) {
        return addressService.getAddressInfoById(id);
    }

    @PostMapping("/address-info-by-table-id")
    public AddressInfo getAddressInfoByTablesId(@RequestBody final TableId tableId) {
        return addressService.getAddressInfoByTableId(tableId);
    }
}
