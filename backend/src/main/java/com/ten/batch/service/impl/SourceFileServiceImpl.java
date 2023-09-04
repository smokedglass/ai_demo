package com.ten.batch.service.impl;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.dao.SourceFileDao;
import com.ten.batch.entity.SourceFile;
import com.ten.batch.service.SourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SourceFileServiceImpl implements SourceFileService{
    @Autowired
    private SourceFileDao sourceFileDao;

    @Override
    public SourceFile insertFileRecord(SourceFile sourceFile){
        return sourceFileDao.save(sourceFile);
    }
}
