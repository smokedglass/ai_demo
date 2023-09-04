package com.ten.batch.service.impl;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.dao.ErrDetailDAO;
import com.ten.batch.entity.ErrFileDetail;
import com.ten.batch.service.ErrDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ErrDetailServiceImpl implements ErrDetailService {
    @Autowired
    private ErrDetailDAO errDetailDAO;

    @Override
    public List<ErrFileDetail> findAll() {
        return errDetailDAO.findAll();
    }

    @Override
    public ErrFileDetail insertErrDetail(ErrFileDetail errFileDetail){
        return errDetailDAO.save(errFileDetail);
    }

    @Override
    public ErrFileDetail findById(Long errDetailId){
        return errDetailDAO.findByErrDetailId(errDetailId);
    }

}
