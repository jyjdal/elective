package com.example.electiveuser.service.impl;

import com.example.electivecommon.constant.DataFileName;
import com.example.electivecommon.constant.Defaults;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electivecommon.global.LoginStatus;
import com.example.electiveuser.dao.StudentDAO;
import com.example.electiveuser.service.BaseLoginService;
import com.example.electiveuser.service.StudentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author admin
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService, BaseLoginService, InitializingBean, DisposableBean {
    private Vector<StudentDAO> students;

    @Resource
    private LoginStatus loginStatus;

    @Override
    public List<StudentDAO> getAll() {
        return this.students.stream().toList();
    }

    @Override
    public boolean hasStudentWithStuId(String stuId) {
        return this.students.stream().anyMatch(student -> student.getStuId().equals(stuId));
    }

    @Override
    public boolean hasStudentWithAccount(String account) {
        return this.students.stream().anyMatch(student -> student.getAccount().equals(account));
    }

    @Override
    public boolean verifyStudent(String account, String password) {
        return this.students.stream().anyMatch(student -> student.getAccount().equals(account)
                && student.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())));
    }

    @Override
    public ElectiveResult addStudent(StudentDAO student) {
        // ??????????????????????????????
        if (hasStudentWithStuId(student.getStuId()) || hasStudentWithAccount(student.getAccount())) {
            return new ElectiveResult(false, "Teacher wordId or account already exists!");
        }

        student.setSelectedCourses(new Vector<>(10));
        // ??????????????????
        this.students.add(student);
        return new ElectiveResult(true, "Successfully added new student account: %s, name: %s, stuId: %s."
                .formatted(student.getAccount(), student.getName(), student.getStuId()));
    }

    @Override
    public ElectiveResult removeStudentByStuId(String stuId) {
        // ????????????????????????????????????????????????
        if (this.students.stream().noneMatch(student -> student.getStuId().equals(stuId))) {
            return new ElectiveResult(false, "Student stuId doesn't exist!");
        }

        this.students.removeIf(teacher -> teacher.getStuId().equals(stuId));
        return new ElectiveResult(true, "Successfully removed student with stuId: %s.".formatted(stuId));
    }

    @Override
    public ElectiveResult resetPasswordByStuId(String stuId) {
        // ????????????????????????????????????????????????stuId?????????
        if (!hasStudentWithStuId(stuId)) {
            return new ElectiveResult(false, "WorkId doesn't exist!");
        }

        // ????????????????????????????????????????????????
        Iterator<StudentDAO> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            StudentDAO student = iterator.next();
            if (student.getStuId().equals(stuId)) {
                // ????????????????????????????????????
                int index = this.students.indexOf(student);
                student.setPassword(DigestUtils.md5DigestAsHex(Defaults.DEFAULT_PASSWORD.getBytes()));
                this.students.setElementAt(student, index);
                // ?????????????????????????????????????????????
                break;
            }
        }
        return new ElectiveResult(true, "Successfully reset password of student: %s.".formatted(stuId));
    }

    @Override
    public ElectiveResult updateAccount(String stuId, String newAccount, String newPassword) {
        if (newPassword.length() == 0) {
            // ????????????????????????????????????????????????
            newPassword = loginStatus.getPassword();
        } else {
            // ??????????????????????????????md5????????????
            newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        }

        // ??????????????????
        Iterator<StudentDAO> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            StudentDAO student = iterator.next();
            if (student.getStuId().equals(stuId)) {
                // ????????????????????????????????????
                int index = this.students.indexOf(student);
                // ??????????????????
                student.setAccount(newAccount);
                student.setPassword(newPassword);
                this.students.setElementAt(student, index);
                // ?????????????????????????????????????????????
                break;
            }
        }

        // ?????????????????????????????????
        loginStatus.setAccount(newAccount);
        loginStatus.setPassword(newPassword);
        return new ElectiveResult(true, "Successfully updated account: %s".formatted(newAccount));
    }

    @Override
    public String getStuIdByAccount(String account) {
        for (StudentDAO student : this.students) {
            if (student.getAccount().equals(account)) {
                return student.getStuId();
            }
        }
        return null;
    }

    @Override
    public List<String> getCourseIdsByStuId(String stuId) {
        for (StudentDAO student : this.students) {
            if (student.getStuId().equals(stuId)) {
                return student.getSelectedCourses().stream().toList();
            }
        }
        return null;
    }

    @Override
    public void addCourse(String stuId, String courseId) {
        Iterator<StudentDAO> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            StudentDAO student = iterator.next();
            if (student.getStuId().equals(stuId)) {
                int index = this.students.indexOf(student);
                Vector<String> courses = student.getSelectedCourses();
                courses.add(courseId);
                student.setSelectedCourses(courses);
                this.students.setElementAt(student, index);
                break;
            }
        }
    }

    @Override
    public void removeCourse(String stuId, String courseId) {
        Iterator<StudentDAO> iterator = this.students.iterator();
        while (iterator.hasNext()) {
            StudentDAO student = iterator.next();
            if (student.getStuId().equals(stuId)) {
                int index = this.students.indexOf(student);
                Vector<String> courses = student.getSelectedCourses();
                courses.remove(courseId);
                student.setSelectedCourses(courses);
                this.students.setElementAt(student, index);
                break;
            }
        }
    }

    @Override
    public List<StudentDAO> getAllLearningCourse(String courseId) {
        return this.students.stream()
                .filter(student -> student.getSelectedCourses().contains(courseId))
                .toList();
    }

    @Override
    public void addRequiredCourse(String courseId) {
        this.students.forEach(student -> {
            Vector<String> courses = student.getSelectedCourses();
            courses.add(courseId);
            student.setSelectedCourses(courses);
        });
    }

    @Override
    public void removeCourseForAll(String courseId) {
        this.students.stream()
                .filter(student -> student.getSelectedCourses().contains(courseId))
                .forEach(student -> {
                    Vector<String> courses = student.getSelectedCourses();
                    courses.remove(courseId);
                    student.setSelectedCourses(courses);
                });
    }

    @Override
    public boolean isCourseSelected(String stuId, String courseId) {
        return this.students.stream()
                .filter(student -> student.getStuId().equals(stuId))
                .allMatch(student -> student.getSelectedCourses().contains(courseId));
    }

    @Override
    public ElectiveResult login(String account, String password) {
        if (!hasStudentWithAccount(account)) {
            return new ElectiveResult(false, "Account doesn't exist.");
        }
        if (!verifyStudent(account, password)) {
            return new ElectiveResult(false, "Password wrong!");
        } else {
            // ???????????????????????????????????????
            loginStatus.setLoggedIn(true);
            loginStatus.setLoginType(LoginType.STUDENT);
            loginStatus.setAccount(account);
            loginStatus.setPassword(password);
            return new ElectiveResult(true, "Successfully logged in.");
        }
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        // ????????????????????????vector???????????????NPE
        this.students = new Vector<>(10);

        File dataFile = new File(DataFileName.STUDENT_FILE_NAME);
        if (!dataFile.exists()) {
            // ??????????????????????????????????????????????????????????????????????????????????????????
            log.info("Data file %s not found, creating an empty list."
                    .formatted(DataFileName.STUDENT_FILE_NAME));
        } else {
            // ??????????????????????????????????????????????????????
            log.info("Data file %s found, start loading teacher data."
                    .formatted(DataFileName.STUDENT_FILE_NAME));
            ObjectMapper mapper = new ObjectMapper();
            this.students = mapper.readValue(dataFile, new TypeReference<>() {
            });
        }
    }

    @Override
    public void destroy() throws IOException {
        // ???????????????????????????
        log.info("Dumping teacher data to file: %s".formatted(DataFileName.STUDENT_FILE_NAME));
        File file = new File(DataFileName.STUDENT_FILE_NAME);
        new ObjectMapper().writeValue(file, this.students);
    }
}
