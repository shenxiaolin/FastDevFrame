package com.fast.dev.frame.http;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.fast.dev.frame.http.callback.ResultHttpCallBack;
import com.fast.dev.frame.http.callback.BaseHttpCallBack;
import com.fast.dev.frame.http.callback.JsonHttpCallBack;
import com.fast.dev.frame.http.callback.StringHttpCallBack;
import com.fast.dev.frame.utils.Constant;
import com.fast.dev.frame.utils.GsonUtils;
import com.fast.dev.frame.utils.LogUtils;
import com.fast.dev.frame.utils.StringUtils;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

/**
 * 说明：Http请求
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/7 16:13
 * <p/>
 * 版本：verson 1.0
 */
public class HttpTask extends AsyncTask<Void,Void,ResponseData>{

    private String method;
    private String url;
    private RequestParams params;
    private BaseHttpCallBack callback;
    private int timeout;
    private String requestKey;
    private Headers headers;
    private OkHttpClient okHttpClient;
    private boolean debug;

    public HttpTask(String method,String url,RequestParams params,BaseHttpCallBack callback,int timeout){
        this.method = method;
        this.url = url;
        this.params = params;
        this.callback = callback;
        this.timeout = timeout;
        if (params == null){
            this.params = new RequestParams();
        }
        this.requestKey = this.params.getHttpTaskKey();
        if (StringUtils.isEmpty(requestKey)){
            requestKey = Constant.Http.DEFAULT_KEY;
        }
        //将请求的url及参数组合成一个唯一请求
        HttpTaskHandler.getInstance().addTask(this.requestKey,this);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        okHttpClient = HttpConfig.get().getOkHttpClient();
        debug = HttpConfig.get().getDebug();
        if (params.headerMap != null){
            this.headers = Headers.of(params.headerMap);
        }
        if (callback != null){
            callback.onStart();
        }
    }

    @Override
    protected ResponseData doInBackground(Void... voids) {
        Response response = null;
        ResponseData responseData = new ResponseData();
        try {
            String srcUrl = url;
            //构建请求Request实例
            Request.Builder builder = new Request.Builder();
            switch (method){
                case Constant.Http.GET:
                    url = UrlUtils.getFullUrl(url,params.getUrlParams());
                    builder.get();
                    break;
                case Constant.Http.POST:
                    RequestBody body = params.getRequestBody();
                    if (body != null){
                        builder.post(new ProgressRequestBody(body,callback));
                    }
                    break;
                case Constant.Http.PUT:
                    RequestBody bodyPut = params.getRequestBody();
                    if (bodyPut != null){
                        builder.put(new ProgressRequestBody(bodyPut,callback));
                    }
                    break;
                case Constant.Http.DELETE:
                    url = UrlUtils.getFullUrl(url,params.getUrlParams());
                    builder.delete();
                    break;
                case Constant.Http.HEAD:
                    url = UrlUtils.getFullUrl(url,params.getUrlParams());
                    builder.head();
                    break;
                case Constant.Http.PATCH:
                    RequestBody bodyPatch = params.getRequestBody();
                    if (bodyPatch != null){
                        builder.put(new ProgressRequestBody(bodyPatch,callback));
                    }
                    break;
            }
            builder.url(url).tag(srcUrl).headers(headers);
            Request request = builder.build();
            if (debug){
                LogUtils.i("url="+url+"?"+params.toString());
            }
            //执行请求
            response = okHttpClient.newCall(request).execute();
        }catch (Exception e){
            LogUtils.e(e);
            if (e instanceof SocketTimeoutException){
                responseData.setTimeout(true);
            }else if (e instanceof InterruptedIOException && TextUtils.equals(e.getMessage(),
                    "timeout")){
                responseData.setTimeout(true);
            }
        }

        //处理获取的请求结果
        if (response != null){
            responseData.setResponseNull(false);
            responseData.setCode(response.code());
            responseData.setMessage(response.message());
            responseData.setSuccess(response.isSuccessful());
            String respBody = "";
            try {
                respBody = response.body().string();
            }catch (IOException e){
                LogUtils.e(e);
            }
            responseData.setResponse(respBody);
            responseData.setHeaders(response.headers());
        }else {
            responseData.setResponseNull(true);
        }
        return responseData;
    }

    @Override
    protected void onPostExecute(ResponseData responseData) {
        super.onPostExecute(responseData);
        //判断请求是否在这个集合中
        if (!HttpTaskHandler.getInstance().contains(requestKey)){
            return;
        }
        //请求得到响应
        if (!responseData.isResponseNull()) {
            if (responseData.isSuccess()) {//成功的请求
                String respBody = responseData.getResponse();
                if (debug){
                    LogUtils.i("url="+url+"\n result="+respBody);
                }
                parseResponseBody(respBody, callback);
            } else {//请求失败
                int code = responseData.getCode();
                String msg = responseData.getMessage();
                if(debug){
                    LogUtils.e("url="+url+"\n response failure code="+code+"\n msg="+msg);
                }
                if (code == 504) {
                    if (callback != null) {
                        callback.onFailure(StringHttpCallBack.ERROR_RESPONSE_TIMEOUT,
                                "网络连接超时");
                    }
                } else {
                    if (callback != null) {
                        callback.onFailure(code, msg);
                    }
                }
            }
        } else {//请求无响应
            if (responseData.isTimeout()) {
                if (callback != null) {
                    callback.onFailure(StringHttpCallBack.ERROR_RESPONSE_TIMEOUT,
                            "网络连接超时");
                }
            } else {
                if (debug){
                    LogUtils.e("url=" + url + "\n response empty");
                }
                if (callback != null) {
                    callback.onFailure(StringHttpCallBack.ERROR_RESPONSE_UNKNOWN, "网络连接异常");
                }
            }
        }

        if (callback != null) {
            callback.onFinish();
        }
    }

    /**
     * 说明：解析响应数据
     * @param result 请求的response 内容
     * @param callback 请求回调
     */
    private void parseResponseBody(String result, BaseHttpCallBack callback) {

        //回调为空，不向下执行
        if(callback == null){
            return;
        }
        if (StringUtils.isEmpty(result)) {
            callback.onFailure(StringHttpCallBack.ERROR_RESPONSE_NULL, "数据请求为空");
        }else {
            if (debug){
                LogUtils.json(result);
            }
            if (callback instanceof StringHttpCallBack){
                callback.onSuccess(result);
            }else if (callback instanceof ResultHttpCallBack){
                try {
                    Object obj = GsonUtils.toBean(result,callback.getClazz());
                    if (obj != null){
                        callback.onSuccess(obj);
                    }else {
                        callback.onFailure(BaseHttpCallBack.ERROR_RESPONSE_JSON_EXCEPTION,"数据解析错误");
                    }
                }catch (Exception e){
                    LogUtils.e(e);
                    callback.onFailure(BaseHttpCallBack.ERROR_RESPONSE_JSON_EXCEPTION, "数据解析错误");
                }
            }else if (callback instanceof JsonHttpCallBack){
                try {
                    ((JsonHttpCallBack) callback).onSuccess(new JSONObject(result));
                }catch (JSONException e){
                    LogUtils.e(e);
                    callback.onFailure(BaseHttpCallBack.ERROR_RESPONSE_JSON_EXCEPTION, "数据解析错误");
                }
            }
        }
    }
}
