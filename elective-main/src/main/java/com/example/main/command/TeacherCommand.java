package com.example.main.command;

import com.example.electivecommon.config.LoginStatus;
import com.example.electiveuser.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Resource;

/**
 * 教师能够执行的所有命令
 *
 * @author admin
 */
@Slf4j
@ShellComponent
public class TeacherCommand {
    @Resource
    private TeacherServiceImpl teacherService;

    @Resource
    private LoginStatus loginStatus;

    /**
     * 变更管理员账号密码
     *
     * @param account  新的账号
     * @param password 新的密码
     */
    @ShellMethod(value = "Update current admin account.", key = "teacher update")
    public void updateTeacher(@ShellOption(defaultValue = "") String account
            , @ShellOption(defaultValue = "") String password) {
//        // 如果都为空值，那么直接返回
//        if (account.length() == 0 && password.length() == 0) {
//            return;
//        }
//        // 至少有一个不为空值，按照逻辑处理
//        if (account.length() != 0 && password.length() != 0) {
//            // 两个都更新，新密码不要写入到日志中
//            log.info("Successfully updated admin account: %s.".formatted(account));
//            teacherService.updateAccount(account, password);
//        } else {
//            if (account.length() == 0) {
//                // 只更新密码
//                log.info("Successfully updated admin account: %s.".formatted(loginStatus.getAccount()));
//                adminService.updateAccount(loginStatus.getAccount(), password);
//            } else {
//                // 只更新账号
//                log.info("Successfully updated admin account: %s.".formatted(account));
//                adminService.updateAccount(account, loginStatus.getPassword());
//            }
//        }
    }
}
