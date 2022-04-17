package net.arver.commerce.advice;

import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.vo.ServiceResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionAdvice.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public ServiceResult<String> handleException(HttpServletRequest request, Exception ex) {
        final ServiceResult<String> ret = new ServiceResult<>(-1, "business error");
        ret.setData(ex.getMessage());
        log.error("commerce service has error:[{}]", ex.getMessage(), ex);
        return ret;
    }
}
