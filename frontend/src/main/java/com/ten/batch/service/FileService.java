package com.ten.batch.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 名称：FileService
 * 功能描述：文件处理接口
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/30 19:33
 */
public interface FileService {
    List<String> uploadFilesByThread(List<String> filePathList) throws ExecutionException, InterruptedException;
}
