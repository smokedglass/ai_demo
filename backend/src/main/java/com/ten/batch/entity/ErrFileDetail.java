package com.ten.batch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "err_file_detail")
@Getter
@Setter
@NoArgsConstructor

public class ErrFileDetail implements Serializable{
    private static final long serialVersionUID = -1L;
    /**
     * 数据库主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long errDetailId;

    /**
     * 汇总金额
     */
    private BigDecimal totalMoney;

    /**
     * 汇总笔数
     */
    private Long totalRows;

    /**
     * 错误格式汇总金额
     */
    private BigDecimal errTotalMoney;

    /**
     * 错误格式汇总笔数
     */
    private Long errTotalRows;

    /**
     * 文件大小
     */
    private Long fileSize;
}
