package com.khoab.tmademo.service;

import com.khoab.tmademo.dao.RoleDAO;
import com.khoab.tmademo.dao.UserDAO;
import com.khoab.tmademo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userDAO.findUserAccount(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        List<String> roleNames = this.roleDAO.getRoleNames(userEntity.getId());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), grantList);
    }
}
