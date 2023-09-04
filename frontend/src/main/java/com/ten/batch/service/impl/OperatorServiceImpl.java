package com.ten.batch.service.impl;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.dao.OperatorDao;
import com.ten.batch.entity.Operator;
import com.ten.batch.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    private OperatorDao operatorDao;

    @Override
    public Operator findByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new CommonException(ResultCode.FAIL);
        }
        Operator operator = operatorDao.findByUserName(userName);
        return operator;
    }
}
