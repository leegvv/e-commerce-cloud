package net.arver.commerce.advice;

import net.arver.commerce.annotation.IgnoreResponseAdvice;
import net.arver.commerce.vo.ServiceResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ServiceResultAdvice.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@RestControllerAdvice
public class ServiceResultAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(final MethodParameter methodParameter,
                            final Class<? extends HttpMessageConverter<?>> clazz) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(final Object body,
                                  final MethodParameter returnType,
                                  final MediaType selectedContentType,
                                  final Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  final ServerHttpRequest request, final ServerHttpResponse response) {
        if (null == body) {
            return new ServiceResult<>(0, "");
        } else if (body instanceof ServiceResult) {
            return body;
        } else {
            return new ServiceResult<>(body);
        }
    }
}
