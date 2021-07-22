package com.khoab.tmademo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role", schema = "user_detail")
@Data
public class RoleEntity {
    @Id
    private int id;

    @Column(name = "role_name")
    private String roleName;
}
