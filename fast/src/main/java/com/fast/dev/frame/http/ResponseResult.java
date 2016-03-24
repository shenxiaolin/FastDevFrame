package com.fast.dev.frame.http;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 说明：返回结果集合
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/8 13:04
 * <p/>
 * 版本：verson 1.0
 */
public class ResponseResult<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<T> list;
    private int count;

    public ResponseResult() {
        this.list = Collections.emptyList();
    }

    public ResponseResult(List<T> list, int count) {
        this.list = list;
        this.count = count;
    }

    public List<T> getResults() {
        return this.list;
    }

    public int getCount() {
        return this.count;
    }
}
