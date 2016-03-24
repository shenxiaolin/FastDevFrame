package com.fast.dev.frame.http;

import com.fast.dev.frame.http.callback.BaseHttpCallBack;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 说明：带处理进度的请求体
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/1/18 18:43
 * <p/>
 * 版本：verson 1.0
 */
public class ProgressRequestBody extends RequestBody{
    //请求体
    private final RequestBody requestBody;
    //回调
    private final BaseHttpCallBack callback;
    //包装完成
    private BufferedSink bufferedSink;
    //加载速度
    private long mPreviousTime;

    public ProgressRequestBody(RequestBody body,BaseHttpCallBack callback){
        this.requestBody = body;
        this.callback = callback;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null){
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink sink(Sink sink){
        mPreviousTime = System.currentTimeMillis();
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;

                //回调
                if (callback!=null) {
                    //计算下载速度
                    long totalTime = (System.currentTimeMillis() - mPreviousTime)/1000;
                    if ( totalTime == 0 ) {
                        totalTime += 1;
                    }
                    long networkSpeed = bytesWritten / totalTime;
                    int progress = (int)(bytesWritten * 100 / contentLength);
                    callback.onProgress(progress, networkSpeed, bytesWritten == contentLength);
                }
            }
        };
    }
}
