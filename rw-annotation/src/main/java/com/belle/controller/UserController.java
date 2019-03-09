package com.belle.controller;

import com.belle.annotation.DS;
import com.belle.entity.UserEntity;
import com.belle.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @DS
    @RequestMapping("read")
    public List<UserEntity> read(){
        return userMapper.getAll();
    }

    /**
     * 只是用查询的操作模拟调用写库（现实中应该是增删改的动作操作写库）
     * @return
     */
    @RequestMapping("write")
    @DS(value = "writeTestDb")
    public UserEntity write(){
        return userMapper.getUserById(1L);
    }
}
