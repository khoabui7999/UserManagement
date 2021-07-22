package com.khoab.tmademo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_role", schema = "user_detail")
@Data
public class UserRoleEntity {
    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
