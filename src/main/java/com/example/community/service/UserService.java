package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void insert(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
            userMapper.insert(user);
        }else {
            user.setId(users.get(0).getId());
            userMapper.updateByPrimaryKey(user);
        }

    }
}
