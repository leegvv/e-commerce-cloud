package net.arver.commerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.account.AddressInfo;
import net.arver.commerce.account.TableId;
import net.arver.commerce.service.AddressService;
import org.springframework.web.bind.annotation.*;

/**
 * AddressController.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Tag(name = "地址管理", description = "AddressController")
@Slf4j
@RequestMapping("/address")
@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(final AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "创建地址信息")
    @PostMapping("/create-address")
    public TableId createAddressInfo(@RequestBody final AddressInfo addressInfo) {
        return addressService.createAddressInfo(addressInfo);
    }

    @Operation(summary = "获取当前地址")
    @GetMapping("/current-address")
    public AddressInfo getCurrentAddressInfo() {
        return addressService.getCurrentAddressInfo();
    }

    @Parameter(name = "id",description = "主键",required = true)
    @Operation(summary = "根据id获取地址信息")
    @GetMapping("/address-info")
    public AddressInfo getAddressInfo(@RequestParam final Long id) {
        return addressService.getAddressInfoById(id);
    }

    @Operation(summary = "根据id列表查询地址信息")
    @PostMapping("/address-info-by-table-id")
    public AddressInfo getAddressInfoByTablesId(@RequestBody final TableId tableId) {
        return addressService.getAddressInfoByTableId(tableId);
    }
}
