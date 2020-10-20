package com.wh.service;

import com.wh.pojo.Users;
import com.wh.pojo.bo.UserBO;
import org.apache.catalina.User;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-14 17:17
 */
public interface UserService {

    /**
     * 判断用户是否存在
     *
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 添加用户
     *
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    Users queryUserForLogin(String username, String password);
}
