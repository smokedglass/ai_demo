package com.ten.batch.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 名称：FIleUtils
 * 功能描述：文件处理工具类
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 16:50
 */
public class FIleUtils {
    public static void main(String[] args) throws IOException {

        //交易比数
        int transaction_sum = 0;
        //交易错误比数
        int transaction_err_sum = 0;
        //交易金额
        double transaction_amount = 0;
        //交易错误金额
        double transaction_err_amount = 0;
        //读取文件名
        String filename = "F:\\file_data\\demo_data.txt";
        //输出文件名
        String out_filename = filename + "err";

        FileWriter writer = null;
        writer = new FileWriter(filename + ".txt");


        //字节流读文件
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line_data;
        while ((line_data = br.readLine()) != null) {
            //错误次数
            int err_total_num = 0;
            //错误内容字符串
            String err_content = "";
            //创建对错字典
            Map<String, String> DN = new HashMap<String, String>();

            //将读取的数据转换为字节
            byte[] lint_byte = line_data.getBytes(StandardCharsets.UTF_8);
            //姓名字段,替换字符串中的空格
            String username = new String(lint_byte, 0, 16).replaceAll("\\s", "");
            if (!checkname(username)) {
                DN.put("姓名", "false");
                err_content += "姓名格式错误";
                err_total_num += 1;
            }

            //身份证字段
            String userIDC = new String(lint_byte, 17, 18).replaceAll("\\s", "");
            if (!judgeIDNumber(userIDC)) {
                DN.put("身份证", "false");
                err_content += "身份证格式错误";
                err_total_num += 1;
            }

            //现金字段
            String userCash = new String(lint_byte, 34, 11).replaceAll("\\s", "");
            if (!isNumeric(userCash)) {
                DN.put("现金金额", "false");
                err_content += "现金金额格式错误";
                err_total_num += 1;
            }

            //邮政编码字段
            String userPost = new String(lint_byte, 45, 6).replaceAll("\\s", "");
            if (!checkpost(userPost)) {
                DN.put("邮编", "false");
                err_content += "邮编格式错误";
                err_total_num += 1;
            }

            //地址字段
            String userAddress = new String(lint_byte, 51, 100).replaceAll("\\s", "");
            if (!isValidAddress(userAddress)) {
                DN.put("家庭住址", "false");
                err_content += "家庭住址格式错误";
                err_total_num += 1;
            }


            double now_cash = Double.parseDouble(userCash);

            //交易次数统计
            transaction_sum++;

            //交易金额汇总
            transaction_amount += now_cash;

            if (DN.containsValue("false")) {
                transaction_err_sum++;
                transaction_err_amount += now_cash;
                err_content = String.format("“%06d”", transaction_sum) + String.format("“%02d”", err_total_num) + err_content;

                //写入错误信息
                writer.write(err_content);

            }//汇总错误次数和错误金额
            //保留两位小数
            System.out.println(String.format("%.2f", transaction_amount));
            //输出错误信息
            System.out.println(err_content);

        }//while

        br.close();
        writer.close();

    }//psvm

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

    //判断名字里包含的字符是否全部为汉字
    public static boolean checkname(String name) {
        int n = 0;
        for (int i = 0; i < name.length(); i++) {
            n = (int) name.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }

    //判断金额是否全部为数字
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
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
                return false;
                //是全为数字
            } else {
                return isNumeric(userpost);
            }
        } else {
            return false;
        }
    }

    //判断地址是否正常
    public static boolean isValidAddress(String address) {
        // 判断地址长度是否在5到100个字符之间
        if (address.length() < 5 || address.length() > 100) {
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
}
