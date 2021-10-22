package com.example.electiveuser.service;

/**
 * @author admin
 */
public interface AdminService {
    /**
     * 验证管理员账号是否存在
     *
     * @param account 需要验证的管理员账号
     * @return 管理员账号是否存在
     */
    boolean hasAdmin(String account);

    /**
     * 验证管理员账号密码是否正确
     *
     * @param account  需要验证的管理员账号
     * @param password 需要验证的管理员密码，需要以md5的形式传递
     * @return 管理员账号密码是否正确
     */
    boolean verifyAdmin(String account, String password);

    /**
     * 更新管理员账号和密码
     *
     * @param newAccount  新的账号
     * @param newPassword 新的密码
     */
    void updateAccount(String newAccount, String newPassword);
}
