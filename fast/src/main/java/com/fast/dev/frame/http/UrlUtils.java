package com.fast.dev.frame.http;

import java.util.Iterator;
import java.util.Map;

/**
 * 说明：工具类，补全url
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/18 18:16
 * <p/>
 * 版本：verson 1.0
 */
public class UrlUtils {

    public static String getFullUrl(String url,Map<String,String> params){
        StringBuffer urlFull = new StringBuffer();
        urlFull.append(url);
        if (urlFull.indexOf("?",0) < 0 && !params.isEmpty()){
            urlFull.append("?");
        }
        Iterator<Map.Entry<String,String>> paramsIterator = params.entrySet().iterator();
        while (paramsIterator.hasNext()){
            Map.Entry<String,String> entry = paramsIterator.next();
            String key = entry.getKey();
            String vaule = entry.getValue();
            urlFull.append(key).append("=").append(vaule);
            if (paramsIterator.hasNext()){
                urlFull.append("&");
            }
        }
        return urlFull.toString();
    }

}
