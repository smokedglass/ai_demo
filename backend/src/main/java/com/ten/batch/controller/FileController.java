package com.ten.batch.controller;

import com.ten.batch.common.CommonException;
import com.ten.batch.common.Result;
import com.ten.batch.common.ResultCode;
import com.ten.batch.entity.ErrFileDetail;
import com.ten.batch.entity.ErrFileLog;
import com.ten.batch.entity.Operator;
import com.ten.batch.entity.SourceFile;
import com.ten.batch.service.ErrDetailService;
import com.ten.batch.service.ErrFileService;
import com.ten.batch.service.OperatorService;
import com.ten.batch.service.SourceFileService;
import org.apache.logging.log4j.core.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 名称：FileController
 * 功能描述：文件控制类
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 17:17
 */

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private SourceFileService sourceFileService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private ErrFileService errFileService;

    @Autowired
    private ErrDetailService errDetailService;

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(String userName, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new Result(ResultCode.FAIL);
        }
        System.out.println("上传的用户名为:" + userName);

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));


        //Todo 存储路径写到配置文件里，全局调用;
        String property = System.getProperty("user.dir") + "\\primaryFiles\\";
        System.out.println("项目路径为:" + property);

        Operator operator = operatorService.findByUserName(userName);
        Long userId = -1l;
        if (operator != null && operator.getUserId() != null) {
            userId = operator.getUserId();
        } else {
            return new Result(ResultCode.USERUPLOADFILEISNOTEXIST);
        }
        System.out.println("上传文件用户Id" + userId);

        //按照userId存储上传的文件
        String filePath = property + userId.toString() + "\\unchecked\\";
        String filePathBackUp = property + userId.toString() + "\\checked\\";

        System.out.println("文件存储路径为:" + filePath);

        //获取新文件名称 命名：时间戳+UUID+后缀
        String newFileName = userId.toString() + "+" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())) + suffixName;
        System.out.println("文件新名称为:" + newFileName);
        // 构建上传路径
        File dest = new File(filePath +  newFileName);
//        File destBackUp = new File(filePathBackUp +  newFileName);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
//
//        if (!destBackUp.getParentFile().exists()) {
//            destBackUp.getParentFile().mkdirs();
//        }

        // 保存文件
//        try {
//            InputStream in = file.getInputStream();
//            FileOutputStream dest = new FileOutputStream(filePath + newFileName);
//            FileOutputStream destBackUp = new FileOutputStream(filePathBackUp + newFileName);
//            for (int c = 0; (c = in.read()) != -1; ) {
//                dest.write(c);
//            }
//            InputStream in2 = file.getInputStream();
//            for (int c = 0; (c = in2.read()) != -1; ) {
//                destBackUp.write(c);
//            }
//            dest.close();
//            destBackUp.close();
//            in.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        try {
             //保存文件
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SourceFile sourceFile = new SourceFile();
        sourceFile.setFileName(newFileName);
        sourceFile.setPath(filePath);
        sourceFile.setUploadTime(new Date());
        sourceFile.setUserId(userId);
        sourceFileService.insertFileRecord(sourceFile);

        ErrFileDetail errFileDetail = new ErrFileDetail();
        errDetailService.insertErrDetail(errFileDetail);

        Long errDetailId = -1l;
        if (errFileDetail.getErrDetailId() > 0) {
            errDetailId = errFileDetail.getErrDetailId();
        }

        ErrFileLog errFileLog = new ErrFileLog();
        errFileLog.setFileName(newFileName);
        errFileLog.setUserId(userId);
        errFileLog.setUserName(userName);
        errFileLog.setErrDetailId(errDetailId);
        errFileService.insertErrFileLog(errFileLog);

        if (sourceFile.getFileId() > 0 && errFileLog.getErrFileId() > 0) {
//                System.out.println("新增文件记录成功，新增的文件信息：");
//                System.out.println("文件id：" + sourceFile.getFileId());
//                System.out.println("文件名：" + sourceFile.getFileName());
//                System.out.println("文件存储路径：" + sourceFile.getPath());
//                System.out.println("文件上传时间：" + sourceFile.getUploadTime());
//                System.out.println("上传文件操作员Id：" + sourceFile.getUserId());
//
//                System.out.println("新增错误文件记录成功====================：");
//                System.out.println("错误文件id：" + errFileLog.getErrFileId());
//                System.out.println("文件名：" + errFileLog.getFileName());
//                System.out.println("用户Id：" + errFileLog.getUserId());
//                System.out.println("用户名：" + errFileLog.getUserName());
            return new Result(ResultCode.SUCCESS);
        } else {
            return new Result(ResultCode.FAIL);
        }

    }

    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) {
        try {
            System.out.println("要下载的文件名:" + fileName);
            // path是指想要下载的文件的路径
            if (fileName == "") {
                throw new CommonException(ResultCode.FAIL);
            }

            String fileNameNew = fileName.replace(" ", "+");

            String path = System.getProperty("user.dir") + "\\errFiles\\" + fileNameNew;
            System.out.println("要下载的文件路径:" + path);

            File file = new File(path);
            if (!file.exists()) {
                throw new CommonException(ResultCode.DOMLOADFILENOTEXIST);
            }
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
