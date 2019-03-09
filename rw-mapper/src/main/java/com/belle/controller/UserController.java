package com.belle.controller;

import com.belle.entity.UserEntity;
import com.belle.mapper.master.MasterMapper;
import com.belle.mapper.slave.SlaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController  {
    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private SlaveMapper slaveMapper;

    /**
     * 查询主库数据
     * @return
     */
    @RequestMapping ("master/getUsers")
    public List<UserEntity> getMasterUserList(){
        return masterMapper.getAll();
    }

    /**
     * 查询从库数据
     * @return
     */
    @RequestMapping ("slave/getUsers")
    public List<UserEntity> getSlaveUserList(){
        return slaveMapper.getAll();
    }
}
