package com.example.electiveuser.service.impl;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.service.AdminService;
import com.example.electiveuser.service.BaseLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Service
public class AdminLoginServiceImpl implements BaseLoginService {
    @Resource
    private AdminService adminService;

    @Resource
    private LoginStatus loginStatus;

    @Override
    public ElectiveResult login(String account, String password) {
        if (!adminService.hasAdmin(account)) {
            return new ElectiveResult(false, "Account doesn't exist.");
        }
        if (!adminService.verifyAdmin(account, password)) {
            return new ElectiveResult(false, "Password wrong!");
        } else {
            // 登录成功，变更当前登录状态
            loginStatus.setLoggedIn(true);
            loginStatus.setLoginType(LoginType.ADMIN);
            loginStatus.setAccount(account);
            loginStatus.setPassword(password);
            return new ElectiveResult(true, "Successfully logged in.");
        }
    }
}
