package com.ten.batch.service;

import com.ten.batch.entity.ErrFileDetail;

import java.util.List;

public interface ErrDetailService {
    public List<ErrFileDetail> findAll();

    public ErrFileDetail insertErrDetail(ErrFileDetail errFileDetail);

    public ErrFileDetail findById(Long errDetailId);
}
