package com.example.electiveuser.dao;

import com.example.electivecommon.constant.Defaults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
    @Builder.Default
    private String id = UUID.randomUUID().toString();

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
     * 教师登录的密码，初始默认密码123456
     */
    @Builder.Default
    private String password = Defaults.DEFAULT_PASSWORD;

    @Override
    public String toString() {
        return "Account: %s, name: %s, workId: %s".formatted(account, name, workId);
    }
}
