package com.ten.batch.dao;

import com.ten.batch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 名称：UserApiDao
 * 功能描述：
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 13:43
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /**
     * 根据电话号码查询用户
     * @param mobile 电话号码
     * @return 用户
     */
    public User findByMobile(String mobile);
}

