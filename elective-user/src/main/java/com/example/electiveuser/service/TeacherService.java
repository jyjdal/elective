package com.example.electiveuser.service;

import com.example.electivecommon.dto.ElectiveResult;
import com.example.electiveuser.dao.TeacherDAO;

import java.util.List;

/**
 * @author admin
 */
public interface TeacherService {
    /**
     * 以列表的形式获取全部的教师
     *
     * @return 全部的教师
     */
    List<TeacherDAO> getAll();

    /**
     * 判断是否有工号符合条件的教师
     *
     * @param workId 需要查找的工号
     * @return 返回是否有符合条件的教师
     */
    boolean hasTeacherWithWorkId(String workId);

    /**
     * 判断是否有账号符合条件的教师
     *
     * @param account 需要查找的账号
     * @return 返回是否有符合额条件的教师
     */
    boolean hasTeacherWithAccount(String account);

    /**
     * 验证教师账号是否正确
     *
     * @param account  教师登录的账号
     * @param password 教师登录的密码，需要以md5的形式传递
     * @return 账号密码是否正确
     */
    boolean verifyTeacher(String account, String password);

    /**
     * 添加新的教师信息
     *
     * @param teacher 需要添加的教师信息
     * @return 返回执行结果
     */
    ElectiveResult addTeacher(TeacherDAO teacher);

    /**
     * 根据工号或账号删除教师信息
     *
     * @param workId 需要删除的教师工号
     * @return 返回删除结果
     */
    ElectiveResult removeTeacherByWorkId(String workId);

    /**
     * 按照工号重置教师密码
     *
     * @param workId 需要重置密码的教师工号
     * @return 返回重置结果
     */
    ElectiveResult resetPasswordByWorkId(String workId);
}
