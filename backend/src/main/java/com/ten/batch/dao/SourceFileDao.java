package com.ten.batch.dao;

import com.ten.batch.entity.SourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SourceFileDao extends JpaRepository<SourceFile, Long>, JpaSpecificationExecutor<SourceFile> {
    /**
     * 添加原文件记录
     * @param sourceFile 原文件
     * @return 文件记录
     */
    public SourceFile save(SourceFile sourceFile);
}

