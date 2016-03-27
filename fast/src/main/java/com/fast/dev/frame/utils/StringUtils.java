package com.fast.dev.frame.utils;

import java.util.regex.Pattern;

/**
 * 说明：字符串工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:26
 * <p/>
 * 版本：verson 1.0
 */

public final class StringUtils {

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private final static Pattern phone = Pattern
            .compile("1\\d{10}$");

    /**
     * 说明：判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 说明：判断多个定字符串是否空
     *      有一个为null或"",返回true
     *      全不为null或全不为""返回false
     */
    public static boolean isEmpty(CharSequence ...input) {
        boolean flag = true;
        if (input != null){
            for (CharSequence c:input){
                if (!isEmpty(c)){
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 说明：判断两个字符串是否相等
     * @param a
     * @param b
     * @return
     */
    public static boolean isEquals(CharSequence a,CharSequence b){
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 说明：比较字符串是否相等（忽视大小写）
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqualsIgnoreCase(String a,String b){
        if (a == b) return true;
        if (a != null && b != null) {
            return a.equalsIgnoreCase(b);
        }
        return false;
    }

    /**
     * 说明：判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        if (isEmpty(email))
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 说明：判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        if (isEmpty(phoneNum))
            return false;
        return phone.matcher(phoneNum).matches();
    }


    /**
     * 说明：String转long
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 说明：String转double
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0D;
    }

    /**
     * 说明：字符串转布尔
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 说明：二进制转十进制
     * @param str 为只包含0，1的32位字符串，并且以0开头
     * @return 转换失败返回-1
     */
    public static String binToDec(String str){
        if (isEmpty(str)) {
            return "";
        }else if (str.length() < 32 || (str.length() == 32 && str.startsWith("0"))) {
            if (str.matches("[0-1;]+")) {
                return Integer.valueOf(str,2).toString();
            }else {
                return "-1";
            }
        }else {
            return "-1";
        }
    }

    /**
     * 说明：十进制转二进制
     * @param str
     * @return 转换失败返回-1
     */
    public static String decToBin(String str){
        if (isEmpty(str)) {
            return "";
        }
        try {
            return Integer.toBinaryString(Integer.parseInt(str));
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 说明：二进制转十六进制
     * @param str
     * @return 转换失败返回-1
     */
    public static String binToHex(String str){
        if (isEmpty(str)) {
            return "";
        }
        try {
            if (str.matches("[0-1;]+")) {
                String dec = binToDec(str);
                return Integer.toHexString(Integer.parseInt(dec));
            }else {
                return "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 说明：十六进制转二进制
     * @param str
     * @return 转换失败返回-1
     */
    public static String hexToBin(String str){
        if (isEmpty(str)) {
            return "";
        }
        try {
            String dec = Integer.valueOf(str,16).toString();
            return decToBin(dec);
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 说明：十进制转十六进制
     * @param str
     * @return 转换失败返回-1
     */
    public static String decToHex(String str){
        if (isEmpty(str)) {
            return "";
        }
        try {
            return Integer.toHexString(Integer.parseInt(str));
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 说明：十六进制转十进制
     * @param str
     * @return 转换失败返回-1
     */
    public static String hexToDec(String str){
        if (isEmpty(str)) {
            return "";
        }
        try {
            return Integer.valueOf(str,16).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 说明：过滤字符串中Emoji表情
     * @param src 源字符串
     * @return 过滤后字符串
     */
    public static String emojiFilter(String src){
        if (isEmpty(src)) {
            return "";
        }else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < src.codePointCount(0, src.length()); i++) {
                int point = src.codePointAt(i);
                if (!isEmojiCharacter(point)) {
                    sb.append((char)point);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 说明：判读字符是否为Emoji表情
     * @param codePoint
     * @return false:不是，true:是
     */
    public static boolean isEmojiCharacter(int codePoint) {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF)
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)
                || (codePoint >= 0x2100 && codePoint <= 0x214F)
                || (codePoint >= 0x2B00 && codePoint <= 0x23FF)
                || (codePoint >= 0x2900 && codePoint <= 0x297F)
                || codePoint >= 0x10000;
    }

    /**
     * 说明：生成32为包含数字和字母的唯一UUID字符串
     * @return 32位长度
     */
    public static String UUID(){
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}

