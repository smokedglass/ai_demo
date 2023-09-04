package com.ten.batch.controller;

import com.ten.batch.common.*;
import com.ten.batch.entity.ErrFileDetail;
import com.ten.batch.entity.ErrFileLog;
import com.ten.batch.entity.Operator;
import com.ten.batch.service.ErrDetailService;
import com.ten.batch.service.ErrFileService;
import com.ten.batch.service.FileService;
import com.ten.batch.service.OperatorService;
import com.ten.batch.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RequestMapping("/manage")
@RestController
public class ManagerController extends BaseController {
    @Autowired
    private OperatorService operatorService;

    @Autowired
    private ErrFileService errFileService;

    @Autowired
    private ErrDetailService errDetailService;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> loginMap) {
        String userName = loginMap.get("userName");
        String password = loginMap.get("password");
        if (StringUtils.isEmpty(userName)) {
            return new Result(ResultCode.USERNAMEORPASSWORDISNULL);
        }

        Operator operator = operatorService.findByUserName(userName);
        //登录失败
        if (operator == null || !operator.getPassword().equals(password)) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        } else {
            //登录成功
            //api权限字符串
            StringBuilder sb = new StringBuilder();
            //获取到所有的可访问API权限
            /*for (Role role : user.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    if (perm.getType() == PermissionConstants.PERMISSION_API) {
                        sb.append(perm.getCode()).append(",");
                    }
                }
            }*/
            Map<String, Object> map = new HashMap<>();
            //可访问的api权限字符串
            map.put("apis", sb.toString());
            map.put("userName", operator.getUserName());
            //map.put("companyId", user.getCompanyId());
            //map.put("companyName", user.getCompanyName());
            //map.put("departmentId", user.getDepartmentId());
            //map.put("departmentName", user.getDepartmentName());
            String token = jwtUtils.createJwt(operator.getUserId().toString(), operator.getUserName(), map);
            return new Result(ResultCode.SUCCESS, token);
        }
    }

    @GetMapping(value = "/getFileLog")
    public Result getFileLog() {
        List<ErrFileLog> errLogList= errFileService.findAll();
        if (errLogList != null && errLogList.size() > 0) {
            for (ErrFileLog errFileLog : errLogList) {
                //打印结果
                System.out.println("错误文件Id：" + errFileLog.getErrFileId());
                System.out.println("错误文件名：" + errFileLog.getErrFileName());
                System.out.println("错误文件存储地址：" + errFileLog.getErrFilePath());
                System.out.println("===========================================");
            }
            return new Result(ResultCode.SUCCESS, errLogList);
        }else{
            return new Result(ResultCode.FAIL);
        }
    }

    @PostMapping(value = "/getFileLog/page")
    public Result getFileByPage(@RequestBody Map<String, String> params) {
        int pageNo = Integer.parseInt(params.get("pageNo"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        String userName = params.get("userName");

        //分页返回错误日志查询结果
        Page<ErrFileLog> errLogList;
        if(StringUtils.isEmpty(userName)){
            //返回全部结果
            errLogList = errFileService.findByPage(pageNo,pageSize);
        }else{
            Long userIdCheck = -1l;
            Operator operator = operatorService.findByUserName(userName);
            if(operator != null && operator.getUserId() != null){
                userIdCheck = operator.getUserId();
            }else{
                return new Result(ResultCode.FAIL);
            }
            System.out.println("请求获取日志列表的用户Id是：" + userIdCheck);
            //根据操作员用户名查看日志列表
            errLogList= errFileService.findByPageAndUserId(pageNo,pageSize,userIdCheck);
        }

        PageResult pageResult = new PageResult();
        pageResult.totalNum = errLogList.getTotalElements();
        pageResult.totalPage = errLogList.getTotalPages();
        pageResult.rows = errLogList.getContent();
        if (errLogList != null && errLogList.getTotalElements() > 0) {
//            for (ErrFileLog errFileLog : errLogList) {
//                //打印结果
//                System.out.println("错误文件Id：" + errFileLog.getErrFileId());
//                System.out.println("错误文件名：" + errFileLog.getErrFileName());
//                System.out.println("错误文件存储地址：" + errFileLog.getErrFilePath());
//                System.out.println("===========================================");
//            }
            return new Result(ResultCode.SUCCESS, pageResult);
        }else{
            System.out.println("===========================================");

            return new Result(ResultCode.FAIL);
        }
    }

    //扫描接口   根据登录状态查找用户Id,进而找到该用户未扫描文件，触发批处理模块，处理结束后，将未扫描文件夹清空
    @GetMapping(value = "/checkFile")
    public Result checkFile() throws ExecutionException, InterruptedException {
        //登录状态下的用户Id
        String folderPath = System.getProperty("user.dir") + "\\primaryFiles\\" + userId + "\\unchecked\\";
        File folder = new File(folderPath);

        //Todo 根据UserId查找日志数据
        // 文件绝对路径
        List<String> filePathList = new ArrayList<>();
        if (folder.exists()) {
            String[] fileNameList = folder.list();
            if (null != fileNameList && fileNameList.length > 0) {
                for (String fileName : fileNameList) {
                    //System.out.println("文件路径下的文件" + fileName);
                    filePathList.add(folderPath + fileName);
                }
            }
        }

//        for(String file : filePathList){
//            System.out.println("文件路径下的文件路径" + file);
//        }

        List<String> resultList = fileService.uploadFilesByThread(filePathList);

        for(String result : resultList){
            String[] resultOne = result.split(",");
            //根据原文件名查找日志记录
            String primaryFilePath = resultOne[0];
            String primaryFileName = "";
            String [] tempList = primaryFilePath.split("\\\\");
            primaryFileName = tempList[tempList.length - 1];
            //System.out.println("解析出的文件名" + primaryFileName);
            ErrFileLog errFileLog = errFileService.findByFileName(primaryFileName);
            long errDetailId = errFileLog.getErrDetailId();

            ErrFileDetail errFileDetail = errDetailService.findById(errDetailId);
            //根据Id 查询细节记录
            long totalRows = Long.parseLong(resultOne[1]);
            BigDecimal totalMoney = new BigDecimal(resultOne[2]);
            long errTotalRows = Long.parseLong(resultOne[3]);
            BigDecimal errTotalMoney = new BigDecimal(resultOne[4]);
            long fileSize = errTotalRows;
            //Todo fileSize 计算
            errFileDetail.setTotalMoney(totalMoney);
            errFileDetail.setTotalRows(totalRows);
            errFileDetail.setErrTotalMoney(errTotalMoney);
            errFileDetail.setErrTotalRows(errTotalRows);
            errFileDetail.setFileSize(fileSize);
            errDetailService.insertErrDetail(errFileDetail);

            String errFileName = primaryFileName.split("\\.")[0] + "err.zip";
            String errFilePath = System.getProperty("user.dir") + "\\errFiles\\" + errFileName;
            errFileLog.setErrFileName(errFileName);
            errFileLog.setErrFilePath(errFilePath);
            errFileService.insertErrFileLog(errFileLog);
        }

        //Todo 处理结束, 删除待扫描目录下的文件

        return new Result(ResultCode.SUCCESS);
    }
}
