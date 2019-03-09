package com.belle.mapper;

import com.belle.entity.UserEntity;

import java.util.List;

public interface UserMapper {
    List<UserEntity> getAll();

    void deleteById(Long id);
}
