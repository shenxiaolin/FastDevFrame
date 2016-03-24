package com.fast.dev.frame.handler;

/**
 * 说明：同步任务
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/2/21 15:22
 * <p/>
 * 版本：verson 1.0
 */
public class SyncRunnable {

    private Runnable mRunnable;
    private boolean isEnd = false;

    public SyncRunnable(Runnable runnable){
        this.mRunnable = runnable;
    }

    public void run(){
        if (!isEnd){
            synchronized (this){
                if (!isEnd){
                    mRunnable.run();
                    isEnd = true;
                    try {
                        this.notifyAll();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void waitRun(){
        if (!isEnd){
            synchronized (this){
                if (!isEnd){
                    try {
                        this.wait();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void waitRun(int time,boolean cancel){
        if (!isEnd){
            synchronized (this){
                if (!isEnd){
                    try {
                        this.wait(time);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if (!isEnd && cancel){
                            isEnd = true;
                        }
                    }
                }
            }
        }
    }

}
