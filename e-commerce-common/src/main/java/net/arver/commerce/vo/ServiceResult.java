package net.arver.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ServiceResult.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> implements Serializable {

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 错误消息.
     */
    private String msg;

    /**
     * 响应数据.
     */
    private T data;

    /**
     * 构造函数.
     * @param code code
     * @param msg msg
     */
    public ServiceResult(final Integer code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceResult(final T data) {
        this.code = 0;
        this.data = data;
    }
}
