package net.arver.commerce.exception;

/**
 * ServiceException.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class ServiceException extends RuntimeException {

    @SuppressWarnings("checkstyle:MutableException")
    private String msg;

    public ServiceException(final String msg) {
        super();
        this.msg = msg;
    }

    public ServiceException(final String msg, final Throwable cause) {
        super(cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
