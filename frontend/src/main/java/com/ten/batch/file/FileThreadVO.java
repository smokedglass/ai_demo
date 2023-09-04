package com.ten.batch.file;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 名称：FileThreadVO
 * 功能描述：
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/29 16:16
 */
@Data
@Accessors(chain = true)
public class FileThreadVO<T> {
    /*private InputStream is;
    private Integer start_line;
    private Integer end_line;
    private List<T> result;*/
    private String result;
}

