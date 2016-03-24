package com.fast.dev.frame.database;

import com.fast.dev.frame.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：一对多延迟加载类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/12/26 15:57
 * <p/>
 * 版本：verson 1.0
 */
public class OneToManyLazyLoader<O, M> {
    O ownerEntity;
    Class<O> ownerClazz;
    Class<M> listItemClazz;
    DBUtils db;

    public OneToManyLazyLoader(O ownerEntity, Class<O> ownerClazz,
                               Class<M> listItemclazz, DBUtils db) {
        this.ownerEntity = ownerEntity;
        this.ownerClazz = ownerClazz;
        this.listItemClazz = listItemclazz;
        this.db = db;
    }

    List<M> entities;

    /**
     * 如果数据未加载，则调用loadOneToMany填充数据
     *
     * @return
     */
    public List<M> getList() {
        if (entities == null) {
            this.db.loadOneToMany(this.ownerEntity, this.ownerClazz,
                    this.listItemClazz);
        }
        if (entities == null) {
            entities = new ArrayList<M>();
        }
        return entities;
    }

    public void setList(List<M> value) {
        entities = value;
    }

}
