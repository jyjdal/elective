package com.example.main.command;

import com.example.main.constant.LoginType;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author admin
 */
@ShellComponent
public class LoginCommand {
    /**
     * 用于以不同的身份登录
     * 忽略下面"method is never used"的警告,类比spring boot中的controller，
     * 没有任何类直接调用这个方法
     * @param type 登录的类型
     */
    @ShellMethod("Login as either an admin, a teacher or a student.")
    public void login(@ShellOption String type) {
        LoginType loginType = LoginType.valueOf(type.toUpperCase());
        System.out.println(loginType);
    }
}
