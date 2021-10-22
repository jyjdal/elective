package com.example.main.command;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.dao.TeacherDAO;
import com.example.electiveuser.service.impl.AdminServiceImpl;
import com.example.electiveuser.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * 管理员能够执行的所有命令
 *
 * @author admin
 */
@Slf4j
@ShellComponent
public class AdminCommand {
    @Resource
    private LoginStatus loginStatus;

    @Resource
    private AdminServiceImpl adminService;

    @Resource
    private TeacherServiceImpl teacherService;

    /**
     * 变更管理员账号密码
     *
     * @param account  新的账号
     * @param password 新的密码
     */
    @ShellMethod(value = "Update current admin account.", key = "admin update")
    public void updateAdmin(@ShellOption(defaultValue = "") String account
            , @ShellOption(defaultValue = "") String password) {
        // 如果都为空值，那么直接返回
        if (account.length() == 0 && password.length() == 0) {
            return;
        }
        // 至少有一个不为空值，按照逻辑处理
        if (account.length() != 0 && password.length() != 0) {
            // 两个都更新，新密码不要写入到日志中
            log.info("Successfully updated admin account: %s.".formatted(account));
            adminService.updateAccount(account, password);
        } else {
            if (account.length() == 0) {
                // 只更新密码
                log.info("Successfully updated admin account: %s.".formatted(loginStatus.getAccount()));
                adminService.updateAccount(loginStatus.getAccount(), password);
            } else {
                // 只更新账号
                log.info("Successfully updated admin account: %s.".formatted(account));
                adminService.updateAccount(account, loginStatus.getPassword());
            }
        }
    }

    /**
     * 显示全部的教师列表
     */
    @ShellMethod(value = "List all teachers.", key = "list teacher")
    public void listTeacher() {
        List<TeacherDAO> result = teacherService.getAll();
        for (TeacherDAO teacher : result) {
            System.out.println(teacher);
        }
        System.out.printf("%d teacher(s) listed.%n", result.size());
    }

    /**
     * 新增教师
     *
     * @param workId   教师的工号
     * @param account  教师的账号
     * @param name     教师的真实姓名
     * @param password 教师的密码
     */
    @ShellMethod(value = "Add a teacher.", key = "add teacher")
    public void addTeacher(@ShellOption String workId, @ShellOption String account
            , @ShellOption String name, @ShellOption(defaultValue = "123456") String password) {
        // 首先构造DAO
        TeacherDAO teacher = new TeacherDAO();
        teacher.setId(UUID.randomUUID().toString());
        teacher.setWorkId(workId);
        teacher.setAccount(account);
        teacher.setName(name);
        teacher.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));

        // 添加教师
        ElectiveResult result = teacherService.addTeacher(teacher);
        if (!result.getSuccess()) {
            System.out.println(result.getMessage());
        } else {
            System.out.println(result.getMessage());
            // 如果没有错误，就写入日志
            log.info("Added teacher account: %s, name: %s, workId: %s."
                    .formatted(account, name, workId));
        }
    }

    /**
     * 根据工号删除教师，工号和账号不能同时为空
     *
     * @param workId 需要删除的教师的工号
     */
    @ShellMethod(value = "Remove a teacher.", key = "remove teacher")
    public void removeTeacher(@ShellOption(defaultValue = "") String workId) {
        // 如果工号为空，我该找谁？
        if (workId.length() == 0) {
            System.out.println("WorkId cannot be empty!");
            return;
        }
        // 查不到这名教师的信息
        if (!teacherService.hasTeacherWithWorkId(workId)) {
            System.out.println("Teacher workId doesn't exist!");
            return;
        }
        // 删除对应的教师信息并计入日志
        teacherService.removeTeacherByWorkId(workId);
        String info = "Removed teacher with workId: %s.".formatted(workId);
        log.info(info);
        // 输出成功信息
        System.out.println(info);
    }

    /**
     * 按照工号重置教师的密码
     * @param workId 需要重置密码的教师工号
     */
    @ShellMethod(value = "Reset a teacher's password.", key = "reset teacher")
    public void resetTeacher(@ShellOption String workId) {
        if (!teacherService.hasTeacherWithWorkId(workId)) {
            System.out.println("WorkId doesn't exist!");
            return;
        }
        // 如果找到了，就重置密码
        teacherService.resetPasswordByWorkId(workId);
        String info = "Successfully reset password of teacher: %s.".formatted(workId);
        log.info(info);
        System.out.println(info);
    }

    /**
     * 所有管理员命令的可见性
     *
     * @return 当前命令是否可用
     */
    @ShellMethodAvailability({"admin update"
            , "list teacher", "add teacher", "remove teacher", "reset teacher"})
    public Availability loginCommandAvailability() {
        return loginStatus.getLoggedIn() && loginStatus.getLoginType() == LoginType.ADMIN ?
                Availability.available() : Availability.unavailable("当前登录身份不是管理员！");
    }
}
