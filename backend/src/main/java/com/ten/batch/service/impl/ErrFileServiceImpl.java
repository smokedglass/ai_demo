package com.ten.batch.service.impl;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.dao.ErrFileDAO;
import com.ten.batch.entity.ErrFileLog;
import com.ten.batch.service.ErrFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ErrFileServiceImpl implements ErrFileService{
    @Autowired
    private ErrFileDAO errFileDAO;

    @Override
    public List<ErrFileLog> findAll() {
        return errFileDAO.findAll();
    }

    @Override
    public ErrFileLog insertErrFileLog(ErrFileLog errFileLog){
        return errFileDAO.save(errFileLog);
    }

    @Override
    public Page<ErrFileLog> findByPage(int pageNo, int pageSize){
        Sort sort = Sort.by(Sort.Direction.DESC,"errFileId");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
//        return errFileDAO.findAll(pageable).getContent();
        return errFileDAO.findAll(pageable);
    }

    @Override
    public Page<ErrFileLog> findByPageAndUserId(int pageNo, int pageSize, Long userId){
        Sort sort = Sort.by(Sort.Direction.DESC,"errFileId");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return errFileDAO.findAllByUserId(pageable,userId);
    }

    @Override
    public ErrFileLog findByFileName(String fileName){
        return errFileDAO.findByFileName(fileName);
    }

}
