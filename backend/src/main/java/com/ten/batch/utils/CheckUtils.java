package com.ten.batch.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CheckUtils {
    public static String check_string(String in_put){
        //隐藏关键字信息
        String hidden_info = "";
        int err_total_num = 0;
        //错误内容字符串
        String err_content = "";
        //创建对错字典
        Map<String,String> DN=new HashMap<String,String>();
        //将读取的数据转换为字节
        byte [] lint_byte = in_put.getBytes();
        //姓名字段,替换字符串中的空格
        String username = new String(lint_byte, 0, 16).replaceAll("\\s", "");
        String username_hidd = username.charAt(0) + "**"+",";
        hidden_info += username_hidd;
        if(!checkname(username)){
            DN.put("姓名","false");
            err_content += "姓名格式错误";
            err_total_num += 1;
        }
        //身份证字段
        String userIDC = new String(lint_byte, 16, 18).replaceAll("\\s", "");
        //System.out.println(userIDC);
        String userIDC_hidd = userIDC.substring(0,7) + "****" + userIDC.substring(11, 18)+",";
        hidden_info += userIDC_hidd;
        if(!(isValidIDCard(userIDC)&&judgeIDNumber(userIDC))){
            DN.put("身份证","false");
            err_content += "身份证格式错误";
            err_total_num += 1;
        }
        //现金字段
        String userCash = new String(lint_byte, 34, 11).replaceAll("\\s", "");
        //System.out.println(userCash);
        String userCashC_hidd = userCash.substring(0,2) + "****"+",";
        hidden_info += userCashC_hidd;
        if(!validateAmount(userCash)){
            DN.put("现金金额","false");
            err_content += "现金金额格式错误";
            err_total_num += 1;
        }

        //邮政编码字段
        String userPost = new String(lint_byte, 45, 6).replaceAll("\\s", "");
        //System.out.println(userPost);
        String userPost_hidd = userPost.substring(0,2) + "**"+userPost.substring(3,5)+",";
        if(!checkpost(userPost)){
            DN.put("邮编","false");
            err_content += "邮编格式错误:";
            err_total_num += 1;
        }
        //地址字段
        String userAddress = new String(lint_byte, 51, 90).replaceAll("\\s", "");
        //System.out.println(userAddress);
        if(userAddress.length()<2){
            hidden_info +="您输入的地址不足两位";
        }else {
            hidden_info += userAddress.substring(0, 2) + "*******";
        }
        if(!isValidAddress(userAddress)){
            err_content += "输入的地址不正确";
            err_total_num += 1;
        }
        return err_content + hidden_info + err_total_num + " " + userCash;
    }
    //判断身份证是否合法
    public static boolean judgeIDNumber(String s) {
        //判断长度不是18，直接返回false
        //public int length()
        //返回此字符串的长度。 长度等于字符串中的数字Unicode code units 。
        if (s.length() != 18) {
            return false;
        } else {
            //判断开头数字为0，直接false
            //public char charAt(int index)
            //返回指定索引处的char值。 指数范围从0到length() - 1 。 序列的第一个char值是索引0 ，下一个索引为1 ，依此类推，就像数组索引一样。
            if (s.charAt(0) == '0') {
                return false;
            }
            //遍历字符串
            for (int i = 0; i < s.length(); i++) {
                //17位以内
                if (i <= 16) {
                    //如果字符在数字之外直接false
                    if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                        return false;
                    }
                } else {
                    //第十七位如果不是数字或者大写字母X返回false
                    if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                        if (s.charAt(i) != 'X') {
                            return false;
                        }
                    }
                }
            }
        }
        //以上条件都不满足时返回true，是合法的身份证号
        return true;
    }
    //判断身份证是否合法
    public static boolean isValidIDCard(String idCard) {
        //正则表达式判断
        final String ID_CARD_PATTERN =
                "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
        return Pattern.matches(ID_CARD_PATTERN, idCard);
    }
    //判断名字里包含的字符是否全部为汉字
    public static boolean checkname(String name) {
        int n = 0;
        for(int i = 0; i < name.length(); i++) {
            n = (int)name.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }
    //判断现金是否符合规范
    public static boolean validateAmount(String amount) {
        //正则表达式前8位数字后面.且最多两位小数
        final String AMOUNT_PATTERN = "^[-+]?\\d{1,5}(\\d{3})*(\\.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(AMOUNT_PATTERN);
        Matcher matcher = pattern.matcher(amount);
        return matcher.matches();
    }
    //判断金额是否全部为数字
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    //判断邮政编码是否正常
    public static boolean checkpost(String userpost) {
        //不为空
        if (userpost != null) {
            //长度不为6
            if (userpost.length() != 6) {
                return true;
                //是全为数字
            } else {
                return isNumeric(userpost);
            }
        }else {
            return false;
        }
    }
    //判断地址是否正常
    public static boolean isValidAddress(String address) {
        // 判断地址长度是否在5到100个字符之间
        if (address.length() < 2 || address.length() > 100) {
            return false;
        }

        // 判断地址是否以汉字、字母或数字开头
        String pattern = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+.*";
        if (!Pattern.matches(pattern, address)) {
            return false;
        }

        // 判断地址是否以空格、逗号、括号等特殊字符结尾
        if (address.endsWith(" ") || address.endsWith(",") || address.endsWith(")") || address.endsWith("(")) {
            return false;
        }

        // 判断地址中是否包含非法字符
        String illegalChars = "[^\\u4e00-\\u9fa5a-zA-Z0-9\\s,()]+";
        return !Pattern.matches(illegalChars, address);
    }
    //读文件
    public static String check_file(String file_in) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        //交易比数
        int transaction_sum = 0;
        //交易错误比数
        int transaction_err_sum = 0;
        //交易金额
        double transaction_amount = 0;
        //交易错误金额
        double transaction_err_amount = 0;
        String out_head = "";
        //输出文件名
        String primaryFilePath = file_in.split("\\.")[0];
        String primaryFileName = "";
        String [] tempList = primaryFilePath.split("\\\\");
        primaryFileName = tempList[tempList.length - 1];

        String path = System.getProperty("user.dir") + "\\errFiles\\";
        String out_filename = path + primaryFileName + "err.txt";
//        String out_filename = file_in.split("\\.")[0] + "err.txt";
//        String zip_filename = file_in.split("\\.")[0] + "err.zip";
        FileWriter writer = null;
        writer = new FileWriter(out_filename);

        //以GBK编码读取文件
        InputStreamReader file = new InputStreamReader(new FileInputStream(file_in),"GBk");
        //字节流读文件
        BufferedReader br = new BufferedReader(file);
        String line_data;
        while( (line_data = br.readLine()) != null) {
            //隐藏关键字信息
            String hidden_info = "";

            int err_total_num = 0;
            //错误内容字符串
            String err_content = "";
            //创建对错字典
            Map<String,String> DN=new HashMap<String,String>();

            //将读取的数据转换为字节
            byte [] lint_byte = line_data.getBytes();
            //姓名字段,替换字符串中的空格
            String username = new String(lint_byte, 0, 16).replaceAll("\\s", "");
            String username_hidd = username.charAt(0) + "**"+",";
            hidden_info += username_hidd;
            if(!checkname(username)){
                DN.put("姓名","false");
                err_content += "姓名格式错误";
                err_total_num += 1;
            }

            //身份证字段
            String userIDC = new String(lint_byte, 16, 18).replaceAll("\\s", "");
            //System.out.println(userIDC);
            String userIDC_hidd = userIDC.substring(0,7) + "****" + userIDC.substring(11, 18)+",";
            hidden_info += userIDC_hidd;
            if(!(isValidIDCard(userIDC)&&judgeIDNumber(userIDC))){
                DN.put("身份证","false");
                err_content += "身份证格式错误";
                err_total_num += 1;
            }

            //现金字段
            String userCash = new String(lint_byte, 34, 11).replaceAll("\\s", "");
            //System.out.println(userCash);
            String userCashC_hidd = userCash.substring(0,2) + "****"+",";
            hidden_info += userCashC_hidd;
            if(!validateAmount(userCash)){
                DN.put("现金金额","false");
                err_content += "现金金额格式错误";
                err_total_num += 1;
            }

            //邮政编码字段
            String userPost = new String(lint_byte, 45, 6).replaceAll("\\s", "");
            //System.out.println(userPost);
            String userPost_hidd = userPost.substring(0,2) + "**"+userPost.substring(3,5)+",";
            hidden_info += userPost_hidd;
            if(!checkpost(userPost)){
                DN.put("邮编","false");
                err_content += "邮编格式错误:";
                err_total_num += 1;
            }
            //地址字段
            String userAddress = new String(lint_byte, 51, 90).replaceAll("\\s", "");
            //System.out.println(userAddress);
            if(userAddress.length()<2){
                hidden_info +="您输入的地址不足两位";
            }else {
                hidden_info += userAddress.substring(0, 2) + "*******";
            }
            if(!isValidAddress(userAddress)){
                err_content += "输入的地址不正确";
                err_total_num += 1;
            }

            //交易次数统计
            transaction_sum++;
            //交易金额
            //if(isNumeric(userCash)) {
            double now_cash = Double.parseDouble(userCash);
            //交易金额汇总
            transaction_amount += now_cash;
            //}
            if (DN.containsValue("false")){
                //错误次数
                transaction_err_sum ++;
                //交易金额汇总
                transaction_err_amount += now_cash;
                //}
                err_content =
                        String.format("“%02d”",transaction_sum )  + String.format("“%02d”",err_total_num ) + err_content;

                //写入错误信息
                writer.write(err_content+"  "+hidden_info);
                writer.write("\r\n");

            }//汇总错误次数和错误金额
        }//while
        //错误次数
        System.out.println(transaction_err_sum);
        //错误金额汇总
        System.out.println(transaction_err_amount);
        //交易总次数
        System.out.println(transaction_sum);
        //交易总金额
        System.out.printf("%.2f%n", transaction_amount);
        br.close();
        writer.close();
        // 将文件明细存入resultMap
        String result = file_in + "," + transaction_sum + "," + transaction_amount + "," + transaction_err_sum + "," + transaction_err_amount;
       /* resultMap.put("num", transaction_sum + "");
        resultMap.put("amount", transaction_amount + "");
        resultMap.put("errNum", transaction_err_sum + "");
        resultMap.put("errAmount", transaction_err_amount + "");*/

        //将错误文件信息写入文件头部
        out_head ="错误总次数"+ transaction_err_sum+"    "+"错误总金额"+ transaction_err_amount+"\r\n" ;
        insert(out_filename, out_head);
        zip_file(out_filename);
        return result;
    }
    //插入头
    public static void insert(String filename, String insertContent) throws IOException {//pos是插入的位置
        File tmp = File.createTempFile("tmp",null);
        tmp.deleteOnExit();
        RandomAccessFile raf = new RandomAccessFile(filename, "rw");
        FileOutputStream tmpOut = new FileOutputStream(tmp);
        FileInputStream tmpIn = new FileInputStream(tmp);
        raf.seek(0);//首先的话是0
        byte[] buf = new byte[64];
        int hasRead = 0;
        while ((hasRead = raf.read(buf)) > 0) {
            //把原有内容读入临时文件
            tmpOut.write(buf, 0, hasRead);

        }
        raf.seek(0);
        raf.write(insertContent.getBytes());
        //追加临时文件的内容
        while ((hasRead = tmpIn.read(buf)) > 0) {
            raf.write(buf, 0, hasRead);
        }

    }

    public static boolean zip_file(String file_root) throws IOException {
        //定义要压缩的文件 也就是说在D盘里有个 demo.txt 的文件(必须要有,否者会有异常,实际应用中可判断);
        final String[] split = file_root.split("\\\\");
        final String file_name = split[split.length - 1];
        final String root = file_root.substring(0, file_root.length() - file_name.length());
        File file = new File(root + File.separator + file_name);
        //定义压缩文件的名称
        File zipFile = new File(root + File.separator + file_name.split("\\.")[0] +".zip");

        //定义输入文件流
        InputStream input = new FileInputStream(file);

        //定义压缩输出流
        ZipOutputStream zipOut = null;

        //实例化压缩输出流,并制定压缩文件的输出路径 就是D盘下,名字叫 demo.zip
        zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

        zipOut.putNextEntry(new ZipEntry(file.getName()));

        //设置注释
        zipOut.setComment("www.demo.com");
        int temp = 0;
        while ((temp = input.read()) != -1) {
            zipOut.write(temp);
        }
        input.close();
        zipOut.close();
        return true;
    }

};


