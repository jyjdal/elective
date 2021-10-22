package com.example.electiveuser.service;

import com.example.electivecommon.dto.ElectiveResult;

/**
 * @author admin
 */
public interface BaseLoginService {
    /**
     * 用于以不同身份进行登录的接口
     *
     * @param account  账户
     * @param password 密码
     * @return 登录状态
     */
    ElectiveResult login(String account, String password);
}
