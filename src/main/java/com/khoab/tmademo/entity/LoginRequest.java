package com.khoab.tmademo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    public String username;
    public String password;
}
