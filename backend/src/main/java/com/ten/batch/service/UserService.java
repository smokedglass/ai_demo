package com.ten.batch.service;

import com.ten.batch.entity.User;

/**
 * 名称：UserService
 * 功能描述：
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 13:56
 */
public interface UserService {
    User findByMobile(String mobile);
}
