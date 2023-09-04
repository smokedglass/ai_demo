package com.ten.batch.service;

import com.ten.batch.entity.Operator;

public interface OperatorService {
    public Operator findByUserName(String userName);
}
