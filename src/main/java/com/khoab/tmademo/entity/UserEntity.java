package com.khoab.tmademo.entity;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user", schema = "user_detail")
@Data
public class UserEntity {

    @Id
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "encrypted_password")
    private String password;


}
