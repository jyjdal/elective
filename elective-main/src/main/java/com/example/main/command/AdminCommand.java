package com.example.main.command;

import com.example.electivecommon.constant.Defaults;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electivecommon.global.LoginStatus;
import com.example.electivecourse.dao.BaseCourseDAO;
import com.example.electivecourse.dao.OptionalCourseDAO;
import com.example.electivecourse.dao.RequiredCourseDAO;
import com.example.electivecourse.service.impl.CourseServiceImpl;
import com.example.electiveuser.dao.StudentDAO;
import com.example.electiveuser.dao.TeacherDAO;
import com.example.electiveuser.service.impl.AdminServiceImpl;
import com.example.electiveuser.service.impl.StudentServiceImpl;
import com.example.electiveuser.service.impl.TeacherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private StudentServiceImpl studentService;

    @Resource
    private CourseServiceImpl courseService;

    /**
     * 变更管理员账号密码
     *
     * @param account  新的账号
     * @param password 新的密码
     */
    @ShellMethod(value = "Update current admin account.", key = "update admin")
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
        result.forEach(System.out::println);
        System.out.printf("%d teacher(s) listed.%n", result.size());
    }

    /**
     * 显示全部的学生列表
     */
    @ShellMethod(value = "List all students.", key = "list student")
    public void listStudent() {
        List<StudentDAO> result = studentService.getAll();
        result.forEach(System.out::println);
        System.out.printf("%d student(s) listed.%n", result.size());
    }

    /**
     * 显示全部的课程列表
     */
    @ShellMethod(value = "List all courses.", key = "list course")
    public void listCourse() {
        List<BaseCourseDAO> result = courseService.getAll();
        result.forEach(System.out::println);
        System.out.printf("%d course(s) listed.%n", result.size());
    }

    /**
     * 显示按照选课人数排序后的的课程列表
     */
    @ShellMethod(value = "List all courses sorted by student number.", key = "list course sorted")
    public void listCourseSorted() {
        List<BaseCourseDAO> result = courseService.getSortedByStuNum();
        result.forEach(System.out::println);
        System.out.printf("%d sorted course(s) listed.%n", result.size());
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
            , @ShellOption String name, @ShellOption(defaultValue = Defaults.DEFAULT_PASSWORD) String password) {
        // 首先构造DAO
        TeacherDAO teacher = TeacherDAO.builder()
                .workId(workId)
                .account(account)
                .name(name)
                .password(DigestUtils.md5DigestAsHex(password.getBytes()))
                .build();

        // 添加教师
        ElectiveResult result = teacherService.addTeacher(teacher);
        System.out.println(result.getMessage());
        if (result.getSuccess()) {
            // 如果没有错误，写入日志
            log.info(result.getMessage());
        }
    }

    /**
     * 新增学生
     *
     * @param stuId    学生的学号
     * @param account  学生的账号
     * @param name     学生的真实姓名
     * @param password 学生的密码
     */
    @ShellMethod(value = "Add a student.", key = "add student")
    public void addStudent(@ShellOption String stuId, @ShellOption String account
            , @ShellOption String name, @ShellOption(defaultValue = Defaults.DEFAULT_PASSWORD) String password) {
        // 首先构造DAO
        StudentDAO student = StudentDAO.builder()
                .stuId(stuId)
                .account(account)
                .name(name)
                .password(DigestUtils.md5DigestAsHex(password.getBytes()))
                .build();

        // 添加学生
        ElectiveResult result = studentService.addStudent(student);
        System.out.println(result.getMessage());
        if (result.getSuccess()) {
            // 如果没有错误，写入日志
            log.info(result.getMessage());
        }
    }

    /**
     * 新增选修课课程
     *
     * @param name          课程名称
     * @param courseId      课程号
     * @param teacherWorkId 授课教师工号
     * @param maxStuNum     最大学生选课数量
     */
    @ShellMethod(value = "Add an optional course.", key = "add optional")
    public void addOptional(@ShellOption String name, @ShellOption String courseId,
                            @ShellOption String teacherWorkId, @ShellOption Integer maxStuNum) {
        // 首先构造DAO
        OptionalCourseDAO course = OptionalCourseDAO
                .builder()
                .name(name)
                .courseId(courseId)
                .teacherWorkId(teacherWorkId)
                .maxStuNum(maxStuNum)
                .build();

        // 添加选修课程
        ElectiveResult result = courseService.addCourse(course);
        // 输出信息
        System.out.println(result.getMessage());
        if (result.getSuccess()) {
            // 如果没有错误，写入日志
            log.info(result.getMessage());
        }
    }

    /**
     * 新增必修课课程
     *
     * @param name          课程名称
     * @param courseId      课程号
     * @param teacherWorkId 授课教师工号
     * @param credit        课程的学分
     */
    @ShellMethod(value = "Add a required course.", key = "add required")
    public void addRequired(@ShellOption String name, @ShellOption String courseId,
                            @ShellOption String teacherWorkId, @ShellOption Double credit) {
        // 首先构造DAO
        RequiredCourseDAO course = RequiredCourseDAO
                .builder()
                .name(name)
                .courseId(courseId)
                .teacherWorkId(teacherWorkId)
                .credit(credit)
                .build();

        // 添加选修课程
        ElectiveResult result = courseService.addCourse(course);
        // 输出信息
        System.out.println(result.getMessage());
        if (result.getSuccess()) {
            // 如果没有错误，写入日志
            log.info(result.getMessage());
        }
    }

    /**
     * 根据工号删除教师
     *
     * @param workId 需要删除的教师的工号
     */
    @ShellMethod(value = "Remove a teacher.", key = "remove teacher")
    public void removeTeacher(@ShellOption String workId) {
        ElectiveResult result = teacherService.removeTeacherByWorkId(workId);
        // 首先输出执行结果
        System.out.println(result.getMessage());
        // 如果成功执行，那么写入日志
        if (result.getSuccess()) {
            log.info(result.getMessage());
        }
    }

    /**
     * 根据学号删除学生
     *
     * @param stuId 需要删除的学生的学号
     */
    @ShellMethod(value = "Remove a student.", key = "remove student")
    public void removeStudent(@ShellOption String stuId) {
        ElectiveResult result = studentService.removeStudentByStuId(stuId);
        // 首先输出执行结果
        System.out.println(result.getMessage());
        // 如果成功执行，那么写入日志
        if (result.getSuccess()) {
            log.info(result.getMessage());
        }
    }

    /**
     * 根据课程号删除课程，这里选择被删除的课程的学生直接退课
     *
     * @param courseId 需要删除的课程的课程号
     */
    @ShellMethod(value = "Remove a course.", key = "remove course")
    public void removeCourse(@ShellOption(defaultValue = "") String courseId) {
        ElectiveResult result = courseService.removeCourseByCourseId(courseId);
        System.out.println(result.getMessage());
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
     * 按照学号重置学生的密码
     *
     * @param stuId 需要重置密码的学生学号
     */
    @ShellMethod(value = "Reset a student's password.", key = "reset student")
    public void resetStudent(@ShellOption String stuId) {
        ElectiveResult result = studentService.resetPasswordByStuId(stuId);
        System.out.println(result.getMessage());
        if (result.getSuccess()) {
            log.info(result.getMessage());
        }
    }

    /**
     * 变更课程的授课教师
     *
     * @param courseId 需要变更的课程号
     * @param workId   需要变更的目标教师工号
     */
    @ShellMethod(value = "Set a course's teacher word id.", key = "set course teacher")
    public void setCourseTeacher(@ShellOption String courseId, @ShellOption String workId) {
        ElectiveResult result = courseService.setTeacherWordId(courseId, workId);
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
    @ShellMethodAvailability({"update admin",
            "list teacher", "add teacher", "remove teacher", "reset teacher",
            "list course", "add optional", "add required", "remove course", "list course sorted",
            "set course teacher",
            "list student", "add student", "remove student", "reset student"})
    public Availability adminCommandAvailability() {
        return loginStatus.getLoggedIn() && loginStatus.getLoginType() == LoginType.ADMIN ?
                Availability.available() : Availability.unavailable("You are not logged in as ADMIN!");
    }
}
