package com.khoab.tmademo.service;

import com.khoab.tmademo.entity.UserEntity;

import java.util.List;

public interface UserService {
     List<UserEntity> findAll();
}
