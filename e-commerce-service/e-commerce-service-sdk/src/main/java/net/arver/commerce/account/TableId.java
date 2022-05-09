package net.arver.commerce.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用 id 对象.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableId {

    /**
     *  数据表记录.
     */
    private List<Id> ids;

    /**
     * 数据表记录主键对象
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id {

        /**
     *  数据表记录主键.
     */
        private Long id;
    }
}
