package com.ten.batch.dao;

import com.ten.batch.entity.ErrFileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ErrDetailDAO extends JpaRepository<ErrFileDetail, Long>, JpaSpecificationExecutor<ErrFileDetail> {
    /**
     * 查询错误文件细节
     * @return 细节列表
     */
    public List<ErrFileDetail> findAll();

    /**
     * 根据Id查询错误文件明细
     * @param errDetailId 错误文件日志
     * @return 错误文件明细
     */
    public ErrFileDetail findByErrDetailId(Long errDetailId);

    /**
     * 新增一条错误文件明细
     * @param errFileDetail 错误文件明细
     * @return 错误文件明细
     */
    public ErrFileDetail save(ErrFileDetail errFileDetail);
}
