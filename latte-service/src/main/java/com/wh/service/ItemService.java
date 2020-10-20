package com.wh.service;

import com.wh.pojo.*;
import com.wh.pojo.vo.CommentLevelCountsVO;
import com.wh.pojo.vo.ItemCommentVO;
import com.wh.pojo.vo.ShopcartVO;
import com.wh.utils.PagedGridResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-21 18:11
 */
public interface ItemService {

    /**
     * 根据商品Id查询详情
     *
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     *
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     *
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);


    /**
     * 根据商品id查询商品的评价（分页）
     *
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level,
                                       Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     *
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort,
                                Integer page, Integer pageSize);

    /**
     * 根据分类Id搜索商品列表
     *
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(Integer catId, String sort,
                                Integer page, Integer pageSize);


    /**
     * 购物车中商品的数据
     *
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象
     *
     * @param ItemsId
     * @return
     */
    ItemsSpec queryItemsSpecById(String ItemsId);

    /**
     * 根据商品id获取商品图片的url
     *
     * @param itemId
     * @return
     */
    String queryItemMainImgById(String itemId);

    /**
     * 扣除库存
     *
     * @param specId
     * @param buyCounts
     */
    void decreaseItemSpecStock(String specId, int buyCounts);

}
