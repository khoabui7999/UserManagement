package com.khoab.tmademo.dao;

import com.khoab.tmademo.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class RoleDAO {
    private EntityManager entityManager;

    @Autowired
    public RoleDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<String> getRoleNames(int userId) {
        String sql = "Select ur.role.roleName from UserRoleEntity ur "
                + " where ur.user.id = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
