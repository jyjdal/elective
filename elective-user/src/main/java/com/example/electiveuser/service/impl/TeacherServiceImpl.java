package com.example.electiveuser.service.impl;

import com.example.electivecommon.global.LoginStatus;
import com.example.electivecommon.constant.DataFileName;
import com.example.electivecommon.constant.Defaults;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.dao.TeacherDAO;
import com.example.electiveuser.service.BaseLoginService;
import com.example.electiveuser.service.TeacherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author admin
 */
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService, BaseLoginService, InitializingBean, DisposableBean {
    private Vector<TeacherDAO> teachers;

    @Resource
    private LoginStatus loginStatus;

    @Override
    public List<TeacherDAO> getAll() {
        return this.teachers.stream().toList();
    }

    @Override
    public boolean hasTeacherWithWorkId(String workId) {
        return this.teachers.stream().anyMatch(teacher -> teacher.getWorkId().equals(workId));
    }

    @Override
    public boolean hasTeacherWithAccount(String account) {
        return this.teachers.stream().anyMatch(teacher -> teacher.getAccount().equals(account));
    }

    @Override
    public boolean verifyTeacher(String account, String password) {
        return this.teachers.stream().anyMatch(teacher -> teacher.getAccount().equals(account)
                && teacher.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())));
    }

    @Override
    public ElectiveResult addTeacher(TeacherDAO teacher) {
        // 如果工号或者账号重复
        if (hasTeacherWithWorkId(teacher.getWorkId()) || hasTeacherWithAccount(teacher.getAccount())) {
            return new ElectiveResult(false, "Teacher wordId or account already exists!");
        }

        // 添加教师信息
        this.teachers.add(teacher);
        return new ElectiveResult(true, "Successfully added new teacher account: %s, name: %s, workId: %s."
                .formatted(teacher.getAccount(), teacher.getName(), teacher.getWorkId()));
    }

    @Override
    public ElectiveResult removeTeacherByWorkId(String workId) {
        // 如果找不到教师工号，返回错误信息
        if (this.teachers.stream().noneMatch(teacher -> teacher.getWorkId().equals(workId))) {
            return new ElectiveResult(false, "Teacher workId doesn't exist!");
        }

        this.teachers.removeIf(teacher -> teacher.getWorkId().equals(workId));
        return new ElectiveResult(true, "Successfully removed teacher with workId: %s.".formatted(workId));
    }

    @Override
    public ElectiveResult resetPasswordByWorkId(String workId) {
        // 首先校验输入参数，检查是否有对应workId的教师
        if (!hasTeacherWithWorkId(workId)) {
            return new ElectiveResult(false, "WorkId doesn't exist!");
        }

        // 如果找到对应的教师，那么重置密码
        Iterator<TeacherDAO> iterator = this.teachers.iterator();
        while (iterator.hasNext()) {
            TeacherDAO teacher = iterator.next();
            if (teacher.getWorkId().equals(workId)) {
                // 要先获取下标，否则找不到
                int index = this.teachers.indexOf(teacher);
                teacher.setPassword(DigestUtils.md5DigestAsHex(Defaults.DEFAULT_PASSWORD.getBytes()));
                this.teachers.setElementAt(teacher, index);
                // 记得跳出循环，避免不必要的比较
                break;
            }
        }
        return new ElectiveResult(true, "Successfully reset password of teacher: %s.".formatted(workId));
    }

    @Override
    public ElectiveResult updateAccount(String workId, String newAccount, String newPassword) {
        if (newPassword.length() == 0) {
            // 密码为空值，转换成当前登录的密码
            newPassword = loginStatus.getPassword();
        } else {
            // 密码不为空值，转换成md5加密格式
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        }

        // 更新账号密码
        Iterator<TeacherDAO> iterator = this.teachers.iterator();
        while (iterator.hasNext()) {
            TeacherDAO teacher = iterator.next();
            if (teacher.getWorkId().equals(workId)) {
                // 要先获取下标，否则找不到
                int index = this.teachers.indexOf(teacher);
                // 更新账号密码
                teacher.setAccount(newAccount);
                teacher.setPassword(newPassword);
                this.teachers.setElementAt(teacher, index);
                // 记得跳出循环，避免不必要的比较
                break;
            }
        }

        // 同时要变更当前登录状态
        loginStatus.setAccount(newAccount);
        loginStatus.setPassword(newPassword);
        return new ElectiveResult(true, "Successfully updated account: %s".formatted(newAccount));
    }

    @Override
    public String getWorkIdByAccount(String account) {
        for (TeacherDAO teacher : this.teachers) {
            if (teacher.getAccount().equals(account)) {
                return teacher.getWorkId();
            }
        }
        return null;
    }

    @Override
    public ElectiveResult login(String account, String password) {
        if (!hasTeacherWithAccount(account)) {
            return new ElectiveResult(false, "Account doesn't exist.");
        }
        if (!verifyTeacher(account, password)) {
            return new ElectiveResult(false, "Password wrong!");
        } else {
            // 登录成功，变更当前登录状态
            loginStatus.setLoggedIn(true);
            loginStatus.setLoginType(LoginType.TEACHER);
            loginStatus.setAccount(account);
            loginStatus.setPassword(password);
            return new ElectiveResult(true, "Successfully logged in.");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 首先创建一个空的vector，防止出现NPE
        this.teachers = new Vector<>(10);

        File dataFile = new File(DataFileName.TEACHER_FILE_NAME);
        if (!dataFile.exists()) {
            // 如果数据文件没找到，代表目前没有教师信息，直接创建空列表即可
            log.info("Data file %s not found, creating an empty list."
                    .formatted(DataFileName.TEACHER_FILE_NAME));
        } else {
            // 如果找到数据文件，那么就加载进来即可
            log.info("Data file %s found, start loading teacher data."
                    .formatted(DataFileName.TEACHER_FILE_NAME));
            ObjectMapper mapper = new ObjectMapper();
            this.teachers = mapper.readValue(dataFile, new TypeReference<>() {
            });
        }
    }

    @Override
    public void destroy() throws Exception {
        // 将当前教师信息导出
        log.info("Dumping teacher data to file: %s".formatted(DataFileName.TEACHER_FILE_NAME));
        File file = new File(DataFileName.TEACHER_FILE_NAME);
        new ObjectMapper().writeValue(file, this.teachers);
    }
}
