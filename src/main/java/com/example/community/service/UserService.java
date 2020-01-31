package com.example.community.service;

import com.example.community.Mapper.UserMapper;
import com.example.community.Model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void insert(User user) {
        if(userMapper.findByAccountId(user) == null){
            userMapper.insert(user);
        }else {
            userMapper.updateByAccountId(user);
        }

    }
}
