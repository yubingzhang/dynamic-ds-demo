package com.belle.controller;

import com.belle.entity.UserEntity;
import com.belle.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("get")
    public List<UserEntity> read(){
        return userMapper.getAll();
    }


    @RequestMapping("delete/{id}")
    public void write(@PathVariable Long id){
         userMapper.deleteById(id);
    }
}
