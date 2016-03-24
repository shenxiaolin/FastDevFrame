package com.fast.dev.frame.http;

import com.fast.dev.frame.utils.FileUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.io.InputStream;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 说明：上传文件IORequest
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/7 15:10
 * <p/>
 * 版本：verson 1.0
 */
public class IORequestBody extends RequestBody{

    private String contentType;
    private long fileSize;
    private InputStream inputStream;

    public IORequestBody(String type,long size,InputStream stream){
        this.contentType = type;
        this.fileSize = size;
        this.inputStream = stream;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        if (this.inputStream != null){
            Source source = null;
            try{
                source = Okio.source(inputStream);
                bufferedSink.writeAll(source);
            }finally {
                FileUtils.closeIO(source);
            }
        }
    }

    @Override
    public long contentLength() throws IOException {
        return fileSize;
    }
}
