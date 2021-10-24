package com.example.electiveuser.service;

import com.example.electivecommon.dto.ElectiveResult;
import com.example.electiveuser.dao.StudentDAO;

import java.util.List;

/**
 * @author admin
 */
public interface StudentService {
    /**
     * 获取全部学生的列表
     *
     * @return 全部的学生信息
     */
    List<StudentDAO> getAll();

    /**
     * 搜索是否有学号为stuId的学生
     *
     * @param stuId 需要搜索的学号
     * @return 是否找到符合条件的学生
     */
    boolean hasStudentWithStuId(String stuId);

    /**
     * 搜索是否有学号为account的学生
     *
     * @param account 需要搜索的账号
     * @return 是否找到符合条件的学生
     */
    boolean hasStudentWithAccount(String account);

    /**
     * 验证学生账号是否正确
     *
     * @param account  学生登录的账号
     * @param password 学生登录的密码，需要以md5的形式传递
     * @return 账号密码是否正确
     */
    boolean verifyStudent(String account, String password);

    /**
     * 新增一名学生
     *
     * @param student 需要添加的学生
     * @return 添加的结果
     */
    ElectiveResult addStudent(StudentDAO student);

    /**
     * 根据学号删除学生信息
     *
     * @param stuId 需要删除的学生学号
     * @return 返回删除结果
     */
    ElectiveResult removeStudentByStuId(String stuId);

    /**
     * 按照学号重置学生密码
     *
     * @param stuId 需要重置密码的学生学号
     * @return 返回重置结果
     */
    ElectiveResult resetPasswordByStuId(String stuId);

    /**
     * 变更学生的账号密码
     *
     *
     * @param stuId 需要更新信息的学生的学号
     * @param newAccount 需要更新的账号
     * @param newPassword 需要更新的密码
     * @return 更新账号密码的结果
     */
    ElectiveResult updateAccount(String stuId, String newAccount, String newPassword);

    /**
     * 根据账号查找学生
     *
     * @param account 需要查找的学生的账号
     * @return 查找到的学生的学号
     */
    String getStuIdByAccount(String account);

    /**
     * 根据学号查找学生所选课程的课号
     *
     * @param stuId 需要查找的学生的学号
     * @return 该学生选择所有课程的课号
     */
    List<String> getCourseIdsByStuId(String stuId);
}
