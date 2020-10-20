package com.wh.service.impl.center;

import com.wh.mapper.UsersMapper;
import com.wh.org.n3r.idworker.Sid;
import com.wh.pojo.Users;
import com.wh.pojo.bo.center.CenterUserBO;
import com.wh.service.center.CenterUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: wh
 * @create: 2020/3/8 11:50
 */
@Slf4j
@Service
@AllArgsConstructor
public class CenterUserServiceImpl implements CenterUserService {

    private UsersMapper usersMapper;
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS , rollbackFor = Exception.class)
    @Override
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }

    @Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
    @Override
    public Users updateUserInfo(CenterUserBO centerUserBO, String userId) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUserBO,userId);
        users.setId(userId);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);

        Users user = usersMapper.selectByPrimaryKey(userId);
        return user;

    }

    @Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users users = new Users();
        users.setId(userId);
        users.setFace(faceUrl);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);

        Users user = usersMapper.selectByPrimaryKey(userId);
        return user;
    }
}
