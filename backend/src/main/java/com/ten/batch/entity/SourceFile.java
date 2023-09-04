package com.ten.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "source_file")
@Getter
@Setter
@NoArgsConstructor

public class SourceFile implements Serializable{
    private static final long serialVersionUID = -1L;
    /**
     * 数据库主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long fileId;

    /**
     * 源文件名
     */
    private String fileName;
    /**
     * 文件存储路径
     */
    private String path;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 上传操作员用户Id
     */
    private Long userId;

}
