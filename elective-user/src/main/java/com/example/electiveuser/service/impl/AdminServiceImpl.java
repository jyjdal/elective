package com.example.electiveuser.service.impl;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.constant.DataFileName;
import com.example.electiveuser.dao.AdminDAO;
import com.example.electiveuser.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author admin
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService, InitializingBean, DisposableBean {
    private AdminDAO currentAdmin;

    @Resource
    private LoginStatus loginStatus;

    @Override
    public boolean hasAdmin(String account) {
        return currentAdmin.getAccount().equals(account);
    }

    @Override
    public boolean verifyAdmin(String account, String password) {
        return currentAdmin.getAccount().equals(account)
                && currentAdmin.getPassword().equals(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void updateAccount(String newAccount, String newPassword) {
        this.currentAdmin.setAccount(newAccount);
        // 更新密码，需要转换成md5格式
        this.currentAdmin.setPassword(DigestUtils.md5DigestAsHex(
                newPassword.getBytes(StandardCharsets.UTF_8)));

        // 由于AdminCommand.updateAdmin命令的可见性，执行到这里的时候一定以账号为account的管理员身份登录
        // 同时要变更当前登录状态
        loginStatus.setAccount(newAccount);
        loginStatus.setPassword(newPassword);
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        File dataFile = new File(DataFileName.ADMIN_FILE_NAME);
        if (!dataFile.exists()) {
            // 如果数据文件不存在，就添加一个默认的管理员账号
            log.info("Data file %s not found, start setting default admin account."
                    .formatted(DataFileName.ADMIN_FILE_NAME));
            currentAdmin = new AdminDAO();
            currentAdmin.setId(UUID.randomUUID().toString());
        } else {
            // 数据文件存在，读取文件加载信息
            log.info("Data file %s found, start loading current admin account."
                    .formatted(DataFileName.ADMIN_FILE_NAME));
            ObjectMapper mapper = new ObjectMapper();
            currentAdmin = mapper.readValue(dataFile, AdminDAO.class);
        }
    }

    @Override
    public void destroy() throws Exception {
        // 将当前管理员信息导出
        log.info("Dumping current admin account to file: %s"
                .formatted(DataFileName.ADMIN_FILE_NAME));
        File file = new File(DataFileName.ADMIN_FILE_NAME);
        new ObjectMapper().writeValue(file, currentAdmin);
    }
}