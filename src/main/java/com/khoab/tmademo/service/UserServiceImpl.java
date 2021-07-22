package com.khoab.tmademo.service;

import com.khoab.tmademo.dao.UserDAO;
import com.khoab.tmademo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<UserEntity> findAll() {
        return userDAO.findAll();
    }
}
