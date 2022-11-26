package net.arver.commerce.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.arver.commerce.constant.CommonConstant;
import net.arver.commerce.util.TokenUtil;
import net.arver.commerce.vo.LoginUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * LoginUserInfoInterceptor.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) throws Exception {

        // 部分请求不需要带有身份信息，即白名单
        if (checkWhiteList(request.getRequestURI())) {
            return true;
        }

        // 从 http header 中拿到token
        final String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenUtil.parseUserInfoFromToken(token);
        } catch (final Exception e) {
            log.error("parse login user info error: [{}]", e.getMessage(), e);
        }
        /*if (loginUserInfo == null) {
            throw new ServiceException("获取用户信息错误");
        }*/

        // 设置当前请求上下文，把用户请求信息填充进去
        AccessContext.set(loginUserInfo);

        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求完成接受后调用，常用于清理资源工作.
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @param ex 异常
     */
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        if (AccessContext.get() != null) {
            AccessContext.remove();
        }
    }

    /**
     * 校验是否是白名单接口.
     * @param url url
     * @return 是否是白名单接口
     */
    private boolean checkWhiteList(final String url) {
        return StringUtils.containsAny(url, "springfox", "swagger", "v2", "webjars", "doc.html");
    }

}
