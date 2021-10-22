package com.example.electiveuser.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDAO {
    /**
     * 存储时的id
     */
    private String id;

    /**
     * 教师的工号
     */
    private String workId;

    /**
     * 教师的登录账号
     */
    private String account;

    /**
     * 教师的实际姓名
     */
    private String name;

    /**
     * 教师登录的密码，初始默认密码位123456
     */
    @Builder.Default
    private String password = "123456";

    @Override
    public String toString() {
        return "Account: %s, name: %s, workId: %s".formatted(account, name, workId);
    }
}
