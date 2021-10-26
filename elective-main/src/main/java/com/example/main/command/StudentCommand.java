package com.example.main.command;

import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electivecommon.global.LoginStatus;
import com.example.electivecourse.dao.BaseCourseDAO;
import com.example.electivecourse.service.impl.CourseServiceImpl;
import com.example.electiveuser.service.impl.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生能够执行的命令
 *
 * @author admin
 */
@Slf4j
@ShellComponent
public class StudentCommand {
    @Resource
    private LoginStatus loginStatus;

    @Resource
    private StudentServiceImpl studentService;

    @Resource
    private CourseServiceImpl courseService;

    /**
     * 变更学生账号密码
     *
     * @param newAccount  新的账号
     * @param newPassword 新的密码
     */
    @ShellMethod(value = "Update current student account.", key = "update student")
    public void updateTeacher(@org.jetbrains.annotations.NotNull @ShellOption(defaultValue = "") String newAccount
            , @ShellOption(defaultValue = "") String newPassword) {
        // 如果都为空值，那么就不作任何处理
        if (newAccount.length() != 0 || newPassword.length() != 0) {
            // 首先处理账号不更新的情况，保证后面工号不能是空指针
            if (newAccount.length() == 0) {
                newAccount = loginStatus.getAccount();
            }

            // 根据登录账号获取工号并更新账号密码
            String workId = studentService.getStuIdByAccount(loginStatus.getAccount());
            ElectiveResult result = studentService.updateAccount(workId, newAccount, newPassword);
            System.out.println(result.getMessage());
            if (result.getSuccess()) {
                log.info(result.getMessage());
            }
        }
    }

    /**
     * 查看学生所选的所有课程
     */
    @ShellMethod(value = "List course selected by a student.", key = "list course student")
    public void listCourseByCourseIds() {
        // 首先获取工号和所选课程的课号
        String stuId = studentService.getStuIdByAccount(loginStatus.getAccount());
        List<String> courseIds = studentService.getCourseIdsByStuId(stuId);
        // 然后进行查找
        List<BaseCourseDAO> result = courseService.getAllByCourseIds(courseIds);
        result.forEach(System.out::println);
        System.out.printf("%d course(s) listed.%n", result.size());
    }

    /**
     * 学生进行选课操作
     *
     * @param courseId 需要选择的课程号
     */
    @ShellMethod(value = "Select a course.", key = "select course")
    public void selectCourse(@ShellOption String courseId) {
        String stuId = studentService.getStuIdByAccount(loginStatus.getAccount());
        if (studentService.isCourseSelected(stuId, courseId)) {
            System.out.println("Course already selected!");
            return;
        }
        // 课程被选中
        ElectiveResult result = courseService.selectCourse(courseId);
        if (result.getSuccess()) {
            // 学生选课
            studentService.addCourse(stuId, courseId);
            log.info(result.getMessage());
        }
        System.out.println(result.getMessage());
    }

    /**
     * 学生进行退课操作
     *
     * @param courseId 需要退课的课程号
     */
    @ShellMethod(value = "Deselect a course.", key = "deselect course")
    public void deselectCourse(@ShellOption String courseId) {
        String stuId = studentService.getStuIdByAccount(loginStatus.getAccount());
        // 课程被退课
        ElectiveResult result = courseService.deselectCourse(courseId);
        if (result.getSuccess()) {
            // 学生退课
            studentService.removeCourse(stuId, courseId);
            log.info(result.getMessage());
        }
        System.out.println(result.getMessage());
    }

    /**
     * 所有学生命令是否可用
     *
     * @return 所有命令的可见性
     */
    @ShellMethodAvailability({"update student", "list course student",
            "select course", "deselect course"})
    public Availability studentCommandAvailability() {
        return loginStatus.getLoggedIn() && loginStatus.getLoginType() == LoginType.STUDENT ?
                Availability.available() : Availability.unavailable("You are not logged in as STUDENT!");
    }
}
