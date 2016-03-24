package com.fast.dev.frame.utils;

import java.text.DecimalFormat;

/**
 * 说明：数字工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/11/1 18:14
 * <p/>
 * 版本：verson 1.0
 */

public class NumberUtils {

    /**
     * 说明：禁止实例化
     */
    private NumberUtils(){}

    private static DecimalFormat decimalFormat;

    private static DecimalFormat getDecimalFormat(){
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat();
        }
        return decimalFormat;
    }

    /**
     * 说明：String转Int
     * @param str 目标String
     * @return int
     */
    public final static int toInt(String str){
        int num = 0;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 说明：String转Int
     * @param str 目标String
     * @param def 默认值
     * @return int
     */
    public final static int toInt(String str,int def){
        int num = def;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 说明：Double转Int
     * @param str 目标String
     * @return int
     */
    public final static int toInt(Double str){
        int num = 0;
        try {
            num = str.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 说明：String转Double
     * @param str 目标String
     * @return
     */
    public final static double toDouble(String str){
        double num = 0.0;
        try {
            num = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 说明：保留N位小数
     * @param d
     * @param n
     * @return
     */
    public final static String saveDecimal(double d,int n){
        DecimalFormat format = getDecimalFormat();
        format.setMaximumFractionDigits(n);
        return format.format(d);
    }

    /**
     * 说明：累加方法
     * @param str
     * @return 返回double
     */
    public final static double addDouble(String...str){
        double total = 0.0;
        for (String s : str) {
            total += toDouble(s);
        }
        return total;
    }

    /**
     * 说明：累加方法
     * @param str
     * @return 返回int
     */
    public final static int addInt(String...str){
        int total = 0;
        for (String s : str) {
            total += toInt(s);
        }
        return total;
    }

    /**
     * 说明：任意类型累加方法
     * @param obj
     * @return 返回double
     */
    public final static double addObj(Object...obj){
        double total = 0.0;
        for (Object object : obj) {
            total += toDouble(String.valueOf(object));
        }
        return total;
    }

}

