package com.example.electiveuser.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDAO {
    /**
     * 存储的id
     */
    private String id;

    /**
     * 登录时的账号
     */
    @Builder.Default
    private String account = "admin";


    /**
     * 登录密码
     */
    @Builder.Default
    private String password = DigestUtils.md5DigestAsHex("1234".getBytes());
}
