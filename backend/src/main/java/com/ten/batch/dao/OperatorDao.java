package com.ten.batch.dao;

import com.ten.batch.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperatorDao extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {
    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 用户
     */
    public Operator findByUserName(String userName);

}
