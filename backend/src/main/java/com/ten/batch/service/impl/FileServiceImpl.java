package com.ten.batch.service.impl;

import com.ten.batch.file.CheckFileThread;
import com.ten.batch.file.FileThreadVO;
import com.ten.batch.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 名称：FileServiceImpl
 * 功能描述：文件处理实现类
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/30 19:34
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.thread.num}")
    private Integer threadNum; //线程数

    @Resource(name = "asyncFilmService")
    private ThreadPoolTaskExecutor executor;  //线程池

    @Override
    public List<String> uploadFilesByThread(List<String> filePathList) throws ExecutionException, InterruptedException {
        List<FileThreadVO> threadVOS = new ArrayList<>(threadNum); //自定义线程实体对象

        for (String filePath : filePathList) {
            FileThreadVO fileThreadVO = new FileThreadVO();
            CheckFileThread checkFileThread = new CheckFileThread(filePath);
            Future<String> submit = executor.submit(checkFileThread);
            fileThreadVO.setResult(submit.get());
            threadVOS.add(fileThreadVO);
        }

        List<String> resultCompleteList = new ArrayList<>(); //整合多个线程的读取结果
        threadVOS.forEach(record -> {
            String result = record.getResult();
            resultCompleteList.add(result);
        });

        return resultCompleteList;
    }
}
