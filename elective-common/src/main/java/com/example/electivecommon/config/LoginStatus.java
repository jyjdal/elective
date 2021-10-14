package com.example.electivecommon.config;

import com.example.electivecommon.enums.LoginType;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 当前选课系统登录状态
 *
 * @author admin
 */
@Data
@Component
public class LoginStatus implements InitializingBean {
    /**
     * 用户是否成功登录
     */
    private Boolean loggedIn;

    /**
     * 成功登陆后用户的登录类型
     */
    private LoginType loginType;

    /**
     * 成功登录后的当前账号
     */
    private String account;

    /**
     * 用于在应用实际创建之前完成登录状态的初始化
     *
     */
    @Override
    public void afterPropertiesSet() {
        loggedIn = false;
        loginType = null;
        account = null;
    }
}
