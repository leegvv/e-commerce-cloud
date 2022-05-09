package net.arver.commerce.filter;

import net.arver.commerce.vo.LoginUserInfo;

/**
 * 使用 ThreadLocal 去单独存储每一个线程携带的 LoginUserInfo 信息.
 * 要及时清理保存到 ThreadLocal 中的用户信息
 * 1. 保证没有资源泄漏
 * 2. 保证线程在重用时，不会出现数据会乱
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
public class LoginUserInfoHolder {

    private static final ThreadLocal<LoginUserInfo> LOGIN_USER_INFO = new ThreadLocal<>();

    public static LoginUserInfo get(){
        return LOGIN_USER_INFO.get();
    }

    public static void set(final LoginUserInfo loginUserInfo) {
        LOGIN_USER_INFO.set(loginUserInfo);
    }

    public static void remove() {
        LOGIN_USER_INFO.remove();
    }
}
