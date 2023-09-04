package com.ten.batch.file;


import com.ten.batch.utils.CheckUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * 名称：CheckFileThread
 * 功能描述：检查文件线程
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/30 15:27
 */
public class CheckFileThread implements Callable<String> {

    private static Logger logger = LogManager.getLogger(CheckFileThread.class);

    private String filePath;    //文件开始读取行数

    public CheckFileThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String call() throws Exception {
        long start = System.currentTimeMillis();
        String result = CheckUtils.check_file(filePath);

        logger.info("线程={} 文件读取完成 总耗时={}毫秒  读取数据={}条",
                Thread.currentThread().getName(), (System.currentTimeMillis() - start), result.split(",")[1]);
        return result;
    }
}
