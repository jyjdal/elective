package com.example.main.command;

import com.example.electiveuser.dto.LoginDTO;
import com.example.electiveuser.controller.UserLoginController;
import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.enums.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Slf4j
@ShellComponent
public class LoginCommand {
    @Resource
    private final LoginStatus loginStatus;

    public LoginCommand(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Resource
    private UserLoginController userLoginController;

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
        LoginDTO loginDTO = userLoginController.login(loginType, account, password);
        System.out.println(loginDTO.getMessage());

        // 如果登陆成功，将信息写入到日志中
        if (loginDTO.getSuccess()) {
            log.info("successfully logged in as type: %s, account: %s"
                    .formatted(loginStatus.getLoginType(), loginStatus.getAccount()));
        }
    }

    /**
     * TODO 正式写代码的时候记得删除这个方法
     * 定义子命令的方法
     */
    @ShellMethod(value = "say hello", key = "login hello")
    public void hello() {
        System.out.println("hello");
        loginStatus.setLoggedIn(true);
        loginStatus.setLoginType(LoginType.ADMIN);
        loginStatus.setAccount("admin");
    }
}
