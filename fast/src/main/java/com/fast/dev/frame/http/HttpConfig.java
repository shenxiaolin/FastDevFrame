package com.fast.dev.frame.http;

import android.content.Context;

import com.fast.dev.frame.FastFrame;
import com.fast.dev.frame.utils.Constant;
import com.fast.dev.frame.utils.StringUtils;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import okio.Buffer;

/**
 * 说明：Http配置器
 *
 *         //初始化HttpConfig
 *                 HttpConfig.Builder httpBuilder = new HttpConfig.Builder(this);
 *                 httpBuilder.build().init();
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 14:05
 * <p/>
 * 版本：verson 1.0
 */
public class HttpConfig {

    private OkHttpClient mOkHttpClient;
    //全局的参数
    private Map<String,String> mCommonParams;
    //全局的头信息
    private Map<String,String> mCommonHeader;
    //证书列表
    private List<InputStream> mCertificateList;
    private HostnameVerifier mHostnameVerifier;
    //设置超时时间
    private int mTimeout;
    //设置日志输出（调试模式）
    private boolean mDebug = false;
    //设置信任所有证书（开发模式使用）
    private boolean mTrustAll = false;

    private static HttpConfig mHttpConfig;

    private HttpConfig(Builder builder){
        this.mCommonHeader = builder.mCommonHeader;
        this.mCommonParams = builder.mCommonParams;
        this.mCertificateList = builder.mCertificateList;
        this.mTimeout = builder.mTimeout;
        this.mHostnameVerifier = builder.mHostnameVerifier;
        this.mDebug = builder.mDebug;
        this.mTrustAll = builder.mTrustAll;
    }

    /**
     * 说明：初始化
     */
    public void init(){
        if (mHttpConfig != null){
            return;
        }else {
            //设置超时时间
            this.mOkHttpClient = OkHttpFactory.create(mTimeout);
            //设置HTTPS证书
            if (mCertificateList != null && !mCertificateList.isEmpty()){
                HttpsCerManager manager = new HttpsCerManager(mOkHttpClient);
                manager.setCertificates(mCertificateList);
            }else if (mTrustAll){
                //设置信任所有证书
                HttpsCerManager manager = new HttpsCerManager(mOkHttpClient);
                manager.setTrustAll();
            }
            if (mHostnameVerifier != null){
                mOkHttpClient.setHostnameVerifier(mHostnameVerifier);
            }
            this.mHttpConfig = this;
        }
    }

    public static class Builder{
        private OkHttpClient mOkHttpClient;
        //全局的参数
        private Map<String,String> mCommonParams;
        //全局的头信息
        private Map<String,String> mCommonHeader;
        //证书列表
        private List<InputStream> mCertificateList;
        private HostnameVerifier mHostnameVerifier;
        //设置超时时间
        private int mTimeout;
        //设置日志输出（调试模式）
        private boolean mDebug = false;
        //设置信任所有证书（开发模式使用）
        private boolean mTrustAll = false;

        public Builder(Context context){
            this.mCommonParams = new HashMap<>();
            this.mCommonHeader = new HashMap<>();
            this.mCertificateList = new ArrayList<>();
            this.mDebug = true;
            this.mDebug = false;
        }

        /**
         * 说明：添加公共参数
         * @param params
         * @return
         */
        public Builder setParams(Map<String,String> params){
            for (Map.Entry<String,String> entry : params.entrySet()){
                if (!StringUtils.isEmpty(entry.getKey())){
                    mCommonParams.put(entry.getKey(),StringUtils.isEmpty(entry.getValue())?"":entry.getValue());
                }
            }
            return this;
        }

        /**
         * 说明：添加公共Header
         * @param header
         * @return
         */
        public Builder setHeader(Map<String,String> header){
            for (Map.Entry<String,String> entry : header.entrySet()){
                if (!StringUtils.isEmpty(entry.getKey())){
                    mCommonHeader.put(entry.getKey(),StringUtils.isEmpty(entry.getValue())?"":entry.getValue());
                }
            }
            return this;
        }

        /**
         * 说明：添加证书
         * @param certificates
         * @return
         */
        public Builder setCertificates(InputStream...certificates){
            for (InputStream is : certificates){
                if (is != null){
                    mCertificateList.add(is);
                }
            }
            return this;
        }

        /**
         * 说明：添加证书
         * @param certificates
         * @return
         */
        public Builder setCertificates(String...certificates){
            for (String s : certificates){
                if (!StringUtils.isEmpty(s)){
                    mCertificateList.add(new Buffer().writeUtf8(s).inputStream());
                }
            }
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * 说明：设置超时时间
         * @param timeout
         * @return
         */
        public Builder setTimeout(int timeout){
            this.mTimeout = timeout;
            return this;
        }

        /**
         * 说明：开启调试模式（输出Log日志）
         * @param debug
         * @return
         */
        public Builder setDebug(boolean debug){
            this.mDebug = debug;
            return this;
        }

        /**
         * 说明：设置信任所有证书（开发模式使用）
         * @param trust
         * @return
         */
        public Builder setTrustAll(boolean trust){
            this.mTrustAll = trust;
            return this;
        }

        public HttpConfig build(){
            return new HttpConfig(this);
        }
    }

    /**
     * 说明：获取Http配置器
     * @return
     */
    public static HttpConfig get(){
        if (mHttpConfig == null){
            return getDefaultHttpConfig();
        }else {
            return mHttpConfig;
        }
    }

    /**
     * 说明：获取默认的配置器
     * @return
     */
    private static HttpConfig getDefaultHttpConfig(){
        HttpConfig config = new Builder(FastFrame.getContext())
                                         .setTimeout(Constant.Http.TIMEOUT)
                                         .build();
        config.init();
        return config;
    }

    /**
     * 说明：获取OkHttpClient客户端
     * @return
     */
    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }

    /**
     * 说明：获取公共参数
     * @return
     */
    public Map<String, String> getCommonParams() {
        return mCommonParams;
    }

    /**
     * 说明：获取证书列表
     * @return
     */
    public List<InputStream> getCertificateList() {
        return mCertificateList;
    }


    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    /**
     * 说明：获取超时时间
     * @return
     */
    public int getTimeout() {
        return mTimeout;
    }

    /**
     * 说明：是否开启调试
     * @return
     */
    public boolean getDebug(){
        return mDebug;
    }

    /**
     * 说明：信任所有证书（开发模式使用）
     * @return
     */
    public boolean getTrustAll(){
        return mTrustAll;
    }

    /**
     * 说明：获取头信息
     * @return
     */
    public Map<String, String> getCommonHeader() {
        return mCommonHeader;
    }

}
