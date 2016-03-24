package com.fast.dev.frame.http;

import android.text.TextUtils;

import com.fast.dev.frame.utils.FileUtils;
import com.fast.dev.frame.utils.StringUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 说明：Http请求参数
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/7 10:15
 * <p/>
 * 版本：verson 1.0
 */
public class RequestParams {

    protected ConcurrentHashMap<String,String> headerMap;
    protected ConcurrentHashMap<String,String> urlParams;
    protected ConcurrentHashMap<String,FileWrapper> fileParams;

    protected HttpTaskKey taskKey;
    private String httpTaskKey;

    public RequestParams(){
        this(null);
    }

    public RequestParams(HttpTaskKey key){
        this.taskKey = key;
        init();
    }

    private void init(){
        headerMap = new ConcurrentHashMap<>();
        urlParams = new ConcurrentHashMap<>();
        fileParams = new ConcurrentHashMap<>();

        headerMap.put("charset", "UTF-8");
        if (taskKey != null){
            this.httpTaskKey = taskKey.getHttpTaskKey();
        }

        //添加公共参数
        Map<String,String> commonParams = HttpConfig.get().getCommonParams();
        if (commonParams  != null && !commonParams.isEmpty()){
            urlParams.putAll(commonParams);
        }
        //添加公共Header
        Map<String,String> commonHeader = HttpConfig.get().getCommonHeader();
        if (commonHeader  != null && !commonHeader.isEmpty()){
            headerMap.putAll(commonHeader);
        }

    }

    public void put(String key,String value){
        if (value == null){
            value = "";
        }
        if (!StringUtils.isEmpty(key)){
            urlParams.put(key,value);
        }
    }

    public void put(String key,int value){
        put(key,String.valueOf(value));
    }

    public void put(String key,boolean value){
        put(key,String.valueOf(value));
    }

    public void put(String key,float value){
        put(key,String.valueOf(value));
    }

    public void put(String key,double value){
        put(key,String.valueOf(value));
    }

    public void put(String key,File file){
        if (file == null || !file.exists()){
            return;
        }
        try {
            boolean isPng = FileUtils.isFileType(file, "png");
            if (isPng){
                put(key,new HttpFileInputStream(new FileInputStream(file),file.getName(),file.length()),ContentType.PNG.getContentType());
            }
            boolean isJpg = FileUtils.isFileType(file,"jpg");
            if (isJpg){
                put(key,new HttpFileInputStream(new FileInputStream(file),file.getName(),file.length()),ContentType.JPEG.getContentType());
            }
            if (!isPng && !isJpg){
                put(key,new HttpFileInputStream(new FileInputStream(file),file.getName(),file.length()),ContentType.TEXT.getContentType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void put(String key,HttpFileInputStream stream,String contentType){
        if (!StringUtils.isEmpty(key) && stream != null){
            fileParams.put(key, new FileWrapper(stream.getInputStream(), stream.getName(), contentType, stream.getFileSize()));
        }
    }

    public void putAll(Map<String,String> map){
        if (map != null && map.size() > 0){
            urlParams.putAll(map);
        }
    }

    public void putHeader(String key,String value){
        if (value == null){
            value = "";
        }
        if (!TextUtils.isEmpty(key)){
            headerMap.put(key, value);
        }
    }

    public void putHeader(String key,int value){
        putHeader(key,String.valueOf(value));
    }

    public void putHeader(String key,float value){
        putHeader(key,String.valueOf(value));
    }

    public void putHeader(String key,double value){
        putHeader(key,String.valueOf(value));
    }

    public void putHeader(String key,boolean value){
        putHeader(key, String.valueOf(value));
    }

    public void clearMap(){
        urlParams.clear();
        fileParams.clear();
    }

    public String toJSON(){
        return new JSONObject(urlParams).toString();
    }

    public Map<String,String> getUrlParams(){
        return urlParams;
    }

    public RequestBody getRequestBody(){
        RequestBody body = null;
        if (fileParams.size() > 0){
            boolean hasData = false;
            MultipartBuilder builder = new MultipartBuilder();
            builder.type(MultipartBuilder.FORM);
            for (ConcurrentHashMap.Entry<String,String> entry:urlParams.entrySet()){
                builder.addFormDataPart(entry.getKey(),entry.getValue());
                hasData = true;
            }
            for (ConcurrentHashMap.Entry<String,FileWrapper> entry:fileParams.entrySet()){
                FileWrapper file = entry.getValue();
                if (file.inputStream != null){
                    hasData = true;
                    builder.addFormDataPart(entry.getKey(),file.getFileName(),new IORequestBody(
                            file.getContentType(),file.getFileSize(),file.getInputStream()
                    ));
                }
            }
            if (hasData){
                body = builder.build();
            }
        }else {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            boolean hasData = false;
            for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                hasData = true;
            }
            if (hasData) {
                body = builder.build();
            }
        }
        return body;
    }

    public String getHttpTaskKey(){
        return httpTaskKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ConcurrentHashMap.Entry<String,String> entry:urlParams.entrySet()){
            if (sb.length() > 0){
                sb.append("&");
            }
            sb.append(entry.getKey()+"="+entry.getValue());
        }
        for (ConcurrentHashMap.Entry<String,FileWrapper> entry:fileParams.entrySet()){
            if (sb.length() > 0){
                sb.append("&");
            }
            sb.append(entry.getKey()+"="+entry.getValue().getContentType());
        }
        return sb.toString();
    }

    public static class FileWrapper{
        private InputStream inputStream;
        private String fileName;
        private String contentType;
        private long fileSize;

        public FileWrapper(InputStream stream,String name,String type,long size){
            this.inputStream = stream;
            this.fileName = name;
            this.contentType = type;
            this.fileSize = size;
        }

        public String getFileName(){
            if (fileName != null){
                return fileName;
            }else {
                return "no file name";
            }
        }
        public String getContentType(){
            return contentType;
        }
        public long getFileSize(){
            return fileSize;
        }
        public InputStream getInputStream(){
            return inputStream;
        }
    }

}
