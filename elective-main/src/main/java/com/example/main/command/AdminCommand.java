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
        // 如果都为空值，那么就不作任何处理
        if (account.length() != 0 || password.length() != 0) {
            ElectiveResult result = adminService.updateAccount(account, password);
            System.out.println(result.getMessage());
            log.info(result.getMessage());
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
            // 如果没有错误，输出信息并写入日志
            System.out.println(result.getMessage());
            log.info(result.getMessage());
        }
    }

    /**
     * 根据工号删除教师，工号和账号不能同时为空
     *
     * @param workId 需要删除的教师的工号
     */
    @ShellMethod(value = "Remove a teacher.", key = "remove teacher")
    public void removeTeacher(@ShellOption(defaultValue = "") String workId) {
        ElectiveResult result = teacherService.removeTeacherByWorkId(workId);
        // 首先输出执行结果
        System.out.println(result.getMessage());
        // 如果成功执行，那么写入日志
        if (result.getSuccess()) {
            log.info(result.getMessage());
        }
    }

    /**
     * 按照工号重置教师的密码
     *
     * @param workId 需要重置密码的教师工号
     */
    @ShellMethod(value = "Reset a teacher's password.", key = "reset teacher")
    public void resetTeacher(@ShellOption String workId) {
        ElectiveResult result = teacherService.resetPasswordByWorkId(workId);
        System.out.println(result.getMessage());
        if (result.getSuccess()) {
            log.info(result.getMessage());
        }
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
