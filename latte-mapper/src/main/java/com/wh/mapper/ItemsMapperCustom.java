package com.wh.mapper;

import com.wh.pojo.vo.ItemCommentVO;
import com.wh.pojo.vo.SearchItemsVO;
import com.wh.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    /**
     * 评论
     *
     * @param map
     * @return
     */
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 搜索的功能
     *
     * @param map
     * @return
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 三级分类查询
     *
     * @param map
     * @return
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    /**
     * 购物车中商品的数据
     *
     * @param specIdsList
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    /**
     * @param specId
     * @param pendingCounts
     * @return
     */
    int decreaseItemSpecStock(@Param("specId") String specId,
                              @Param("pendingCounts") int pendingCounts);

}