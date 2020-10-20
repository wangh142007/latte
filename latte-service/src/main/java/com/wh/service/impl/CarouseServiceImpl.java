package com.wh.service.impl;

import com.wh.mapper.CarouselMapper;
import com.wh.pojo.Carousel;
import com.wh.service.CarouseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-21 18:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class CarouseServiceImpl implements CarouseService {

    private CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
