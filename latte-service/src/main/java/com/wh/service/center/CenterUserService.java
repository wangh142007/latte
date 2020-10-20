package com.wh.service.center;

import com.wh.pojo.Users;
import com.wh.pojo.bo.center.CenterUserBO;

/**
 * @author: wh
 * @create: 2020/3/8 11:48
 */
public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     *
     * @param centerUserBO
     * @param userId
     * @return
     */
    Users updateUserInfo(CenterUserBO centerUserBO, String userId);


    /**
     * 用户头像更新
     *
     * @param userId
     * @param faceUrl
     * @return
     */
    Users updateUserFace(String userId, String faceUrl);

}
