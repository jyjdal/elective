package com.example.electiveuser.service;

/**
 * @author admin
 */
public interface AdminService {
    /**
     * 验证管理员账号是否存在
     * @param account 需要验证的管理员账号
     * @return 管理员账号是否存在
     */
    boolean accountExists(String account);

    /**
     * 验证管理员账号密码是否正确
     * @param account 需要验证的管理员账号
     * @param password 需要验证的管理员密码
     * @return 管理员账号密码是否正确
     */
    boolean verifyAccount(String account, String password);
}
