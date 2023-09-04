package com.ten.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "err_file_log")
@Getter
@Setter
@NoArgsConstructor

public class ErrFileLog implements Serializable{
    private static final long serialVersionUID = -1L;
    /**
     * 数据库主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long errFileId;

    /**
     * 错误文件名
     */
    private String errFileName;

    /**
     * 原文件名
     */
    private String fileName;

    /**
     * 操作员Id
     */
    private Long userId;

    /**
     * 操作员用户名
     */
    private String userName;

    /**
     * 文件存储路径
     */
    private String errFilePath;

    /**
     * 错误文件明细Id
     */
    private Long errDetailId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "errDetailId", referencedColumnName = "errDetailId",insertable=false,updatable=false)
    private ErrFileDetail errFileDetail;

}
