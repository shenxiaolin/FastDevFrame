package com.fast.dev.frame.http.download;

import android.os.Message;

import com.fast.dev.frame.http.HttpConfig;
import com.fast.dev.frame.utils.FileUtils;
import com.fast.dev.frame.utils.LogUtils;
import com.fast.dev.frame.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 说明：DownloadHttpTask
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/8 22:07
 * <p/>
 * 版本：verson 1.0
 */
public class DownloadHttpTask extends Thread{

    private int RESULT_NET_ERROR = -1;
    private int RESULT_OTHER_ERROR = 0;
    private int RESULT_SUCCESS = 1;

    private DownloadInfo mDownloadInfo;
    private DownloadNextTaskListener mDownloadNextTaskListener;
    private DownloadUIHandler mDownloadUIHandler;
    private ProgressReportingRandomAccessFile mRandomAccessFile;

    private OkHttpClient mOkHttpClient;

    //开始下载事件，用户计算加载速度
    private long mPreViousTime = 0;
    //任务终止标记
    private boolean mInterrupt;
    private final int BUFFER_SIZE = 8 * 1024;

    public DownloadHttpTask(DownloadInfo info,DownloadUIHandler handler,DownloadNextTaskListener listener){
        this.mDownloadInfo = info;
        this.mDownloadNextTaskListener = listener;
        this.mOkHttpClient = HttpConfig.get().getOkHttpClient();
        this.mDownloadUIHandler = handler;
    }

    @Override
    public void run() {
        onPreExecute();
        int result = request();
        if (result == RESULT_SUCCESS){
            mDownloadInfo.setState(DownloadInfo.COMPLETE);
        }else {
            mDownloadInfo.setState(DownloadInfo.PAUSE);
            mInterrupt = true;
        }
        mDownloadNextTaskListener.nextTask();
        postMessage();
    }

    private int request(){
        String url = mDownloadInfo.getUrl();
        int code = RESULT_SUCCESS;
        long startPos = 0;
        String fileName = mDownloadInfo.getTargetName();
        if (StringUtils.isEmpty(fileName)){
            fileName = FileUtils.getFileNameByUrl(url);
        }
        File file = new File(mDownloadInfo.getTargetFolder(),fileName);
        if (StringUtils.isEmpty(mDownloadInfo.getTargetPath())){
            mDownloadInfo.setTargetPath(file.getAbsolutePath());
        }
        if (file.exists()){
            startPos = file.length();
        }else {
            try{
                if (!file.createNewFile()){
                    code = RESULT_OTHER_ERROR;
                    LogUtils.e("create new file error! file=" + file.getAbsolutePath());
                    return code;
                }
            }catch (IOException e){
                LogUtils.e(e + " file = "+file.getAbsolutePath());
                code = RESULT_OTHER_ERROR;
            }
        }

        //设置断点写文件
        try{
            mRandomAccessFile = new ProgressReportingRandomAccessFile(file,"rw",startPos);
        }catch (FileNotFoundException e){
            LogUtils.e(e);
            code = RESULT_OTHER_ERROR;
            return code;
        }
        Request request = new Request.Builder().url(url).header("RANGE","bytes="+startPos+"-").build();
        //执行请求
        Response response = null;
        try{
            response = mOkHttpClient.newCall(request).execute();
        }catch (IOException e){
            LogUtils.e(e);
            code = RESULT_OTHER_ERROR;
            return code;
        }
        if (response == null || !response.isSuccessful()){
            if (response != null){
                LogUtils.e("download file is error! code="+response.code()+" url="+url);
            }
            code = RESULT_NET_ERROR;
        }else {
            try{
                InputStream is = response.body().byteStream();
                long totalLength = response.body().contentLength();
                mDownloadInfo.setTotalLength(totalLength);
                //文件大小大于总文件大小
                if (startPos > mDownloadInfo.getTotalLength()){
                    file.delete();
                    mDownloadInfo.setProgress(0);
                    mDownloadInfo.setDownloadLength(0);
                    mDownloadInfo.setTotalLength(0);
                    return code;
                }
                if (startPos == mDownloadInfo.getTotalLength() && startPos > 0){
                    publishProgress(100);
                    return code;
                }
                //读写文件流
                int bytesCopied = download(is,mRandomAccessFile);
                if ((startPos + bytesCopied) != mDownloadInfo.getTotalLength() || mInterrupt){
                    //文件下载失败
                    code = RESULT_OTHER_ERROR;
                    return code;
                }
            }catch (IOException e){
                code = RESULT_OTHER_ERROR;
            }catch (Exception e){
                code = RESULT_OTHER_ERROR;
            }
        }
        return code;
    }

    /**
     * 说明：执行文件下载
     * @param input
     * @param out
     * @return
     * @throws Exception
     */
    public int download(InputStream input,RandomAccessFile out) throws Exception{
        if (input == null || out == null){
            return -1;
        }
        byte[] buf = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(input,BUFFER_SIZE);
        int count = 0,n = 0;
        try{
            out.seek(out.length());
            while(!mInterrupt){
                n = bis.read(buf,0,BUFFER_SIZE);
                if (n == -1){
                    break;
                }
                if (mInterrupt){
                    throw new RuntimeException("task interrupt");
                }
                out.write(buf,0,n);
                count += n;
            }
        }finally {
            mOkHttpClient = null;
            FileUtils.closeIO(out,input,bis);
        }
        return count;
    }

    /**
     * 说明：预下载
     */
    private void onPreExecute(){
        mPreViousTime = System.currentTimeMillis();
        mDownloadInfo.setState(DownloadInfo.DOWNLOADING);
        postMessage();
    }

    private final class ProgressReportingRandomAccessFile extends RandomAccessFile{

        private long lastLength = 0;
        private long curLength = 0;
        private long lastRefreshUiTime;

        public ProgressReportingRandomAccessFile(File file, String mode,long len) throws FileNotFoundException {
            super(file, mode);
            this.lastLength = len;
            this.lastRefreshUiTime = System.currentTimeMillis();
        }

        @Override
        public void write(byte[] buffer,int offset,int count) throws IOException {
            super.write(buffer,offset,count);
            curLength += count;
            lastLength = lastLength + count;
            mDownloadInfo.setDownloadLength(lastLength);
            //计算下载速度
            long totalTime = (System.currentTimeMillis()-mPreViousTime)/1000;
            if (totalTime == 0){
                totalTime += 1;
            }
            long networkSpeed = curLength / totalTime;
            mDownloadInfo.setNetworkSpeed(networkSpeed);
            //下载进度
            int progress = (int)((lastLength * 100) / mDownloadInfo.getTotalLength());
            mDownloadInfo.setProgress(progress);
            long curTime = System.currentTimeMillis();
            if (curTime - lastRefreshUiTime >= 1000 || progress == 100){
                publishProgress(progress);
                lastRefreshUiTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * 说明：更新UI
     * @param progress
     */
    private void publishProgress(int progress){
        mDownloadInfo.setProgress(progress);
        if (!mInterrupt){
            if (progress == 100){
                mDownloadInfo.setState(DownloadInfo.COMPLETE);
                mDownloadNextTaskListener.nextTask();
            }else {
                mDownloadInfo.setState(DownloadInfo.DOWNLOADING);
            }
        }
        postMessage();
    }

    public boolean isInterrupt(){
        return mInterrupt;
    }

    public void setInterrupt(boolean interrupt){
        this.mInterrupt = interrupt;
        if (mInterrupt){
            mDownloadInfo.setState(DownloadInfo.PAUSE);
            postMessage();
            mDownloadNextTaskListener.nextTask();
        }
    }

    private void postMessage(){
        Message msg = mDownloadUIHandler.obtainMessage();
        msg.obj = mDownloadInfo;
        mDownloadUIHandler.sendMessage(msg);
    }
}
