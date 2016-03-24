package com.fast.dev.frame.database;

import com.fast.dev.frame.DBUtils;

/**
 * 说明：多对一延迟加载类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:56
 * <p/>
 * 版本：verson 1.0
 */
public class ManyToOneLazyLoader<M, O> {
    M manyEntity;
    Class<M> manyClazz;
    Class<O> oneClazz;
    DBUtils db;

    public ManyToOneLazyLoader(M manyEntity, Class<M> manyClazz,
                               Class<O> oneClazz, DBUtils db) {
        this.manyEntity = manyEntity;
        this.manyClazz = manyClazz;
        this.oneClazz = oneClazz;
        this.db = db;
    }

    O oneEntity;
    boolean hasLoaded = false;

    /**
     * 如果数据未加载，则调用loadManyToOne填充数据
     *
     * @return
     */
    public O get() {
        if (oneEntity == null && !hasLoaded) {
            this.db.loadManyToOne(this.manyEntity, this.manyClazz,
                    this.oneClazz);
            hasLoaded = true;
        }
        return oneEntity;
    }

    public void set(O value) {
        oneEntity = value;
    }

}

