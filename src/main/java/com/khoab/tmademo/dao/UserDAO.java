package com.khoab.tmademo.dao;

import com.khoab.tmademo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserDAO{

    private EntityManager entityManager;

    @Autowired
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserEntity findUserAccount(String userName) {
        try {
            String sql = "Select e from UserEntity e Where e.username = :userName ";

            Query query = entityManager.createQuery(sql, UserEntity.class);
            query.setParameter("userName", userName);

            return (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserEntity> findAll() {
        try {
            Query query = entityManager.createQuery("Select e from UserEntity e", UserEntity.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
