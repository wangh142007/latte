package com.wh.service;

import com.wh.pojo.Carousel;
import com.wh.pojo.UserAddress;
import com.wh.pojo.bo.AddressBO;

import java.util.List;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-21 18:11
 */
public interface AddressService {

    /**
     * 查询所有收货列表
     *
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    void addNewUserAddress(AddressBO addressBO);


    /**
     * 修改地址
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 删除地址
     * @param userId
     * @param addressId
     */
    void deleteUserAddress(String userId, String addressId);


    /**
     * 设置默认地址
     * @param userId
     * @param addressId
     * @return
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id查询具体用户地址对象
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress queryUserAddress(String userId, String addressId);

}
