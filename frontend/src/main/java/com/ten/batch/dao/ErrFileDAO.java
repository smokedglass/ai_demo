package com.ten.batch.dao;

import com.ten.batch.entity.ErrFileLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ErrFileDAO extends JpaRepository<ErrFileLog, Long>, JpaSpecificationExecutor<ErrFileLog> {
    /**
     * 查询错误文件日志列表
     * @return 日志列表
     */
    public List<ErrFileLog> findAll();

    /**
     * 分页查询错误文件日志列表
     * @return 分页日志列表
     */
    //Todo 倒序查询
//    public Page<ErrFileLog> findAll(Pageable pageable, Sort sort);
    public Page<ErrFileLog> findAll(Pageable pageable);

    /**
     * 根据用户Id查询错误文件日志列表
     * @return 分页日志列表
     */
    //Todo 根据用户Id倒序查询
    public Page<ErrFileLog> findAllByUserId(Pageable pageable, Long userId);

    /**
     * 新增一条错误文件日志或修改
     * @param errFileLog 错误文件日志
     * @return 错误文件日志
     */
    public ErrFileLog save(ErrFileLog errFileLog);

    /**
     * 根据原文件名查询日志记录
     * @param fileName 错误文件日志
     * @return 分页日志列表
     */
    public ErrFileLog findByFileName(String fileName);

}
