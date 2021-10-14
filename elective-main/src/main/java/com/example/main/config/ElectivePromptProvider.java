package com.example.main.config;

import com.example.electivecommon.config.LoginStatus;
import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
public class ElectivePromptProvider implements PromptProvider {
    private final LoginStatus loginStatus;

    @Autowired
    public ElectivePromptProvider(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * 自定义命令行的提示信息
     */
    @Override
    public AttributedString getPrompt() {
        // 添加前面的信息
        StringBuilder loginPrompt = new StringBuilder("Elective@");
        if (!loginStatus.getLoggedIn()) {
            // 如果没有登录，就在'@'后面添加没有登录的提示
            loginPrompt.append("not logged in");
        } else {
            // 如果已经登录，那么添加登录状态和账户名
            loginPrompt.append("%s %s".
                    formatted(loginStatus.getLoginType(), loginStatus.getAccount()));
        }
        // 添加提示信息的最后一部分并返回
        String loginPromptEnd = " :>";
        return new AttributedString(loginPrompt.append(loginPromptEnd).toString());
    }
}
