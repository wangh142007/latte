package com.wh.service;

import com.wh.pojo.Carousel;
import com.wh.pojo.OrderStatus;
import com.wh.pojo.bo.ShopcartBO;
import com.wh.pojo.bo.SubmitOrderBO;
import com.wh.pojo.vo.OrderVO;

import java.util.List;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-21 18:11
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param submitOrderBO
     * @return
     */
    OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId, Integer orderStatus);


    /**
     * 查询订单状态
     *
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付的订单
     */
    void closeOrder();

}
