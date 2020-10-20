package com.wh.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.wh.enmus.YesOrNo;
import com.wh.mapper.*;
import com.wh.org.n3r.idworker.Sid;
import com.wh.pojo.OrderItems;
import com.wh.pojo.OrderStatus;
import com.wh.pojo.Orders;
import com.wh.pojo.bo.center.OrderItemsCommentBO;
import com.wh.pojo.vo.MyCommentVO;
import com.wh.service.center.MyCommentsService;
import com.wh.utils.PagedGridResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wh
 * @create: 2020/3/8 11:50
 */
@Slf4j
@Service
@AllArgsConstructor
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    public OrderItemsMapper orderItemsMapper;
    public OrdersMapper ordersMapper;
    public OrderStatusMapper orderStatusMapper;
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PagedGridResult queryMyComments(String userId,
                                           Integer page,
                                           Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPagedGrid(list, page);
    }
}
