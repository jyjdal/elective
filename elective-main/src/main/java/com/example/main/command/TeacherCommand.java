package com.example.main.command;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electivecourse.dao.BaseCourseDAO;
import com.example.electivecourse.service.impl.CourseServiceImpl;
import com.example.electiveuser.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师能够执行的所有命令
 *
 * @author admin
 */
@Slf4j
@ShellComponent
public class TeacherCommand {
    @Resource
    private CourseServiceImpl courseService;

    @Resource
    private TeacherServiceImpl teacherService;

    @Resource
    private LoginStatus loginStatus;

    /**
     * 变更管理员账号密码
     *
     * @param newAccount  新的账号
     * @param newPassword 新的密码
     */
    @ShellMethod(value = "Update current admin newAccount.", key = "update teacher")
    public void updateTeacher(@ShellOption(defaultValue = "") String newAccount
            , @ShellOption(defaultValue = "") String newPassword) {
        // 如果都为空值，那么就不作任何处理
        if (newAccount.length() != 0 || newPassword.length() != 0) {
            // 首先处理账号不更新的情况，保证后面工号不能是空指针
            if (newAccount.length() == 0) {
                newAccount = loginStatus.getAccount();
            }

            // 根据登录账号获取工号
            String workId = teacherService.getWorkIdByAccount(loginStatus.getAccount());
            ElectiveResult result = teacherService.updateAccount(workId, newAccount, newPassword);
            System.out.println(result.getMessage());
            if (result.getSuccess()) {
                log.info(result.getMessage());
            }
        }
    }

    /**
     * 查看教师教授的所有课程
     *
     */
    @ShellMethod(value = "List course taught by a teacher.", key = "list course teacher")
    public void listCourseByTeacherWorkId() {
        // 首先获取工号
        String workId = teacherService.getWorkIdByAccount(loginStatus.getAccount());
        // 然后进行查找
        List<BaseCourseDAO> result = courseService.getAllByTeacherWorkId(workId);
        result.forEach(System.out::println);
        System.out.printf("%d course(s) listed.%n", result.size());
    }

    /**
     * 所有教师命令是否可用
     *
     * @return 教师命令的可用性
     */
    @ShellMethodAvailability({"update teacher", "list course teacher"})
    public Availability teacherCommandAvailability() {
        return loginStatus.getLoggedIn() && loginStatus.getLoginType() == LoginType.TEACHER ?
                Availability.available() : Availability.unavailable("You are not logged in as TEACHER!");
    }
}
