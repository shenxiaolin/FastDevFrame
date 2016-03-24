package com.fast.dev.frame.database;

import java.util.LinkedList;

/**
 * 说明：sql语句的全部信息
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:29
 * <p/>
 * 版本：verson 1.0
 */
public class SqlInfo {

    private String sql;
    private LinkedList<Object> bindArgs;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public LinkedList<Object> getBindArgs() {
        return bindArgs;
    }

    public void setBindArgs(LinkedList<Object> bindArgs) {
        this.bindArgs = bindArgs;
    }

    public Object[] getBindArgsAsArray() {
        if (bindArgs != null)
            return bindArgs.toArray();
        return null;
    }

    public String[] getBindArgsAsStringArray() {
        if (bindArgs != null) {
            String[] strings = new String[bindArgs.size()];
            for (int i = 0; i < bindArgs.size(); i++) {
                strings[i] = bindArgs.get(i).toString();
            }
            return strings;
        }
        return null;
    }

    public void addValue(Object obj) {
        if (bindArgs == null)
            bindArgs = new LinkedList<Object>();

        bindArgs.add(obj);
    }
}
