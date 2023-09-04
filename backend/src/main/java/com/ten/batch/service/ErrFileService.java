package com.ten.batch.service;

import com.ten.batch.entity.ErrFileLog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ErrFileService {
    public List<ErrFileLog> findAll();

    public ErrFileLog insertErrFileLog(ErrFileLog errFileLog);

    public Page<ErrFileLog> findByPage(int pageNo, int pageSize);

    public Page<ErrFileLog> findByPageAndUserId(int pageNo, int pageSize, Long userId);

    public ErrFileLog findByFileName(String fileName);

}
