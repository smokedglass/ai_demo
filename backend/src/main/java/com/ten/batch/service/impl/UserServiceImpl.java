package com.ten.batch.service.impl;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.dao.UserDao;
import com.ten.batch.entity.User;
import com.ten.batch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 名称：UserServiceImpl
 * 功能描述：
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 13:57
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            throw new CommonException(ResultCode.FAIL);
        }
        User user = userDao.findByMobile(mobile);
        return user;
    }
}
