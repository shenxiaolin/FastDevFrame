package com.fast.dev.frame.utils;

/**
 * 说明：资源工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:25
 * <p/>
 * 版本：verson 1.0
 */

import android.content.Context;

import com.fast.dev.frame.FastFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtils {

    private static Context mContext;

    private static void check(){
        if (mContext == null){
            mContext = FastFrame.getContext();
        }
    }

    /**
     * 说明：禁止实例化
     */
    private ResourceUtils(){}

    /**
     * 说明：从Assets中获取数据流
     * @param fileName 文件名
     * @return
     */
    public static InputStream getInputFromAssets(String fileName){
        check();
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }else {
            InputStreamReader reader = null;
            try {
                return mContext.getAssets().open(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 说明：从Assets中获取文件
     * @param fileName 文件名
     * @return
     */
    public static String getFileFromAssets(String fileName){
        check();
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }else {
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(mContext.getAssets().open(fileName));
                return readFile(reader);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 说明：从Raw中获取文件
     * @return
     */
    public static String getFileFromRaw(int id){
        check();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(mContext.getResources().openRawResource(id));
            return readFile(reader);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 说明：从Assets中获取文件内容集合
     * @param fileName 文件名
     * @return
     */
    public static List<String> getFileToListFromAssets(String fileName){
        check();
        if (StringUtils.isEmpty(fileName)){
            return null;
        }else {
            InputStreamReader reader = null;
            List<String> list = new ArrayList<String>();
            BufferedReader bufferedReader = null;
            try {
                reader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
                bufferedReader = new BufferedReader(reader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    list.add(line);
                }
                return list;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                FileUtils.closeIO(reader,bufferedReader);
            }
        }
    }

    /**
     * 说明：从Raw中获取文件内容集合
     * @return
     */
    public static List<String> getFileToListFromRaw(int id){
        check();
        InputStreamReader reader = null;
        reader = new InputStreamReader(mContext.getResources().openRawResource(id));
        List<String> list = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            FileUtils.closeIO(reader,bufferedReader);
        }
    }

    /**
     * 说明：读取文件内容
     * @param reader
     * @return
     */
    private static String readFile(Reader reader){
        if (reader == null) {
            return null;
        }else {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(reader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                FileUtils.closeIO(reader,bufferedReader);
            }
        }
    }

}

