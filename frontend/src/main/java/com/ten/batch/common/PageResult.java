package com.ten.batch.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 名称：PageResult
 * 描述：分页
 *  {
 *      "success"："成功"，
 *      "code"：10000
 *      "message"："ok"，
 *      "data"：{
 *          total：//总条数
 *          rows ：//数据列表
 *      }
 *  }
 *
 * @version 1.0
 * @author: Zhang BoRU
 * @datetime: 2022-04-25 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 21321321321L;
    public Long totalNum;
    public Integer totalPage;
    public List<T> rows;
}
