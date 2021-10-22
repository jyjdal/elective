package com.example.electiveuser.service.impl;

import com.example.electivecommon.constant.DataFileName;
import com.example.electivecommon.constant.Password;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electiveuser.dao.TeacherDAO;
import com.example.electiveuser.service.TeacherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author admin
 */
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService, InitializingBean, DisposableBean {
    private Vector<TeacherDAO> teachers;

    @Override
    public List<TeacherDAO> getAll() {
        return this.teachers.stream().toList();
    }

    @Override
    public boolean hasTeacherWithWorkId(String workId) {
        for (TeacherDAO teacher : this.teachers) {
            if (teacher.getWorkId().equals(workId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTeacherWithAccount(String account) {
        for (TeacherDAO teacher : this.teachers) {
            if (teacher.getAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verifyTeacher(String account, String password) {
        for (TeacherDAO teacher : this.teachers) {
            if (teacher.getAccount().equals(account) &&
                    teacher.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ElectiveResult addTeacher(TeacherDAO teacher) {
        if (this.hasTeacherWithWorkId(teacher.getWorkId())) {
            return new ElectiveResult(false, "Teacher wordId or account already exists!");
        }
        this.teachers.add(teacher);
        return new ElectiveResult(true, "Successfully added new teacher.");
    }

    @Override
    public void removeTeacherByWorkId(String workId) {
        // 遍历过程中删除一定要使用迭代器
        this.teachers.removeIf(teacher -> teacher.getWorkId().equals(workId));
    }

    @Override
    public void resetPasswordByWorkId(String workId) {
        Iterator<TeacherDAO> iterator = this.teachers.iterator();
        while (iterator.hasNext()) {
            TeacherDAO teacher = iterator.next();
            if (teacher.getWorkId().equals(workId)) {
                // 要先获取下标，否则找不到
                int index = this.teachers.indexOf(teacher);
                teacher.setPassword(DigestUtils.md5DigestAsHex(Password.DEFAULT_PASSWORD.getBytes(StandardCharsets.UTF_8)));
                this.teachers.setElementAt(teacher, index);
                return;
            }
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
            this.teachers = new Vector<>(10);
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
        // 将当前管理员信息导出
        log.info("Dumping teacher data to file: %s".formatted(DataFileName.TEACHER_FILE_NAME));
        File file = new File(DataFileName.TEACHER_FILE_NAME);
        new ObjectMapper().writeValue(file, this.teachers);
    }
}
