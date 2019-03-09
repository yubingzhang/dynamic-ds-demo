package com.belle.mapper;

import com.belle.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {
    List<UserEntity> getAll();

    UserEntity getUserById(Long id);
}
