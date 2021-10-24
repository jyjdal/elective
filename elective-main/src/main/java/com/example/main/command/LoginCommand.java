package com.example.main.command;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.controller.UserLoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Resource;

/**
 * 所有和用户登录相关的命令都在这里
 *
 * @author admin
 */
@Slf4j
@ShellComponent
public class LoginCommand {
    @Resource
    private final LoginStatus loginStatus;
    @Resource
    private UserLoginController userLoginController;

    public LoginCommand(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * 用于以不同的身份登录
     * 忽略下面"method is never used"的警告,类比spring boot中的controller，
     * 没有任何类直接调用这个方法
     *
     * @param type 登录的类型
     */
    @ShellMethod("Login as either an admin, a teacher or a student.")
    public void login(@ShellOption String type, @ShellOption String account, @ShellOption String password) {
        // 首先获取登录类型
        LoginType loginType = LoginType.valueOf(type.toUpperCase());

        // 传入登录类型、账号和密码进行登录
        ElectiveResult result = userLoginController.login(loginType, account, password);
        System.out.println(result.getMessage());

        // 如果登陆成功，将信息写入到日志中
        if (result.getSuccess()) {
            log.info("Successfully logged in as type: %s, account: %s"
                    .formatted(loginStatus.getLoginType(), loginStatus.getAccount()));
        }
    }

    /**
     * 登录命令的可用性
     *
     * @return 登录命令是否可用
     */
    @ShellMethodAvailability({"login"})
    public Availability loginAvailability() {
        return !loginStatus.getLoggedIn() ?
                Availability.available() : Availability.unavailable("Already logged in!");
    }

    /**
     * 登陆成功后的退出登录方法
     */
    @ShellMethod("Logout from current account.")
    public void logout() {
        loginStatus.setLoggedIn(false);
        loginStatus.setLoginType(null);
        loginStatus.setAccount(null);
        loginStatus.setPassword(null);
    }

    /**
     * 退出登录命令的可用性
     *
     * @return 退出登录命令是否可用
     */
    @ShellMethodAvailability({"logout"})
    public Availability logoutAvailability() {
        String message = "未登录用户无法退出登录！";
        return loginStatus.getLoggedIn() ?
                Availability.available() : Availability.unavailable(message);
    }
}
