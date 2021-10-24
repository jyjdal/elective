package com.example.electiveuser.dao;

import com.example.electivecommon.constant.Defaults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import java.util.UUID;

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
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    /**
     * 登录时的账号
     */
    @Builder.Default
    private String account = Defaults.DEFAULT_ADMIN_ACCOUNT;


    /**
     * 登录密码
     */
    @Builder.Default
    private String password = DigestUtils.md5DigestAsHex(Defaults.DEFAULT_ADMIN_PASSWORD.getBytes());
}
