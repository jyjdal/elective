package com.example.electiveuser.dao;

import com.example.electivecommon.constant.Defaults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.Vector;

/**
 * 学生类
 *
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDAO {
    /**
     * 存储时的id
     */
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    /**
     * 学生的学号
     */
    private String stuId;

    /**
     * 学生的登录账号
     */
    private String account;

    /**
     * 学生的实际姓名
     */
    private String name;

    /**
     * 学生登录的密码，初始默认密码123456
     */
    @Builder.Default
    private String password = Defaults.DEFAULT_PASSWORD;

    /**
     * 学生选择所有课程的课号
     */
    @Builder.Default
    private Vector<String> selectedCourses = new Vector<>(10);

    @Override
    public String toString() {
        return "Student: %s, name: %s, stuId: %s.".formatted(account, name, stuId);
    }
}
