package com.wh.mapper;

import com.wh.my.mapper.MyMapper;
import com.wh.pojo.Category;
import com.wh.pojo.vo.CategoryVO;
import com.wh.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
    /**
     * 获取二级的分类
     *
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);


    /**
     * 查询首页每个一级分类下的6条最新商品数据
     *
     * @param map
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}