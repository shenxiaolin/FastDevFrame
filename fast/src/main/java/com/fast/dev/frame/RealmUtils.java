package com.fast.dev.frame;

import android.content.Context;

import com.fast.dev.frame.utils.LogUtils;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * 说明：Realm工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2016/2/17 10:52
 * <p/>
 * 版本：verson 1.0
 */
public class RealmUtils {

    private Realm mRealm;
    private static HashMap<String,RealmUtils> realmUtilsMap = new HashMap<>();

    private RealmUtils(Realm realm) {
        mRealm = realm;
    }

    public synchronized static RealmUtils get(Context context,String name){
        RealmUtils utils = realmUtilsMap.get(name);
        if (utils == null){
            RealmConfiguration configuration = new RealmConfiguration.Builder(context)
                    .name(name).build();
            Realm realm = Realm.getInstance(configuration);
            utils = new RealmUtils(realm);
            realmUtilsMap.put(name,utils);
        }
        return utils;
    }

    private Realm getRealm(){
        return mRealm;
    }

    /**
     * 说明：添加操作
     */
    public <T extends RealmObject> boolean insert(T t){
        boolean result = true;
        try {
            getRealm().beginTransaction();
            getRealm().copyToRealm(t);
            getRealm().commitTransaction();
        }catch (Exception e){
            LogUtils.e(e);
            getRealm().cancelTransaction();
            result  = false;
        }finally {
            return result;
        }
    }

    /**
     * 说明：删除所有数据
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RealmObject> boolean deleteAll(Class<T> clazz){
        boolean result = true;
        try {
            RealmResults<T> results = findAll(clazz);
            getRealm().beginTransaction();
            if (results != null){
                results.clear();
            }
            getRealm().commitTransaction();
        }catch (Exception e){
            LogUtils.e(e);
            getRealm().cancelTransaction();
            result  = false;
        }finally {
            return result;
        }
    }

    /**
     * 说明：删除数据
     * @param clazz
     * @param where
     * @param <T>
     * @return
     */
    public <T extends RealmObject> boolean delete(Class<T> clazz,String... where){
        boolean result = true;
        try {
            RealmResults<T> results = findAll(clazz, where);
            getRealm().beginTransaction();
            if (results != null){
                results.clear();
            }
            getRealm().commitTransaction();
        }catch (Exception e){
            LogUtils.e(e);
            getRealm().cancelTransaction();
            result  = false;
        }finally {
            return result;
        }
    }

    /**
     * 说明：获取查询对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RealmObject> RealmQuery<T> where(Class<T> clazz){
        return getRealm().where(clazz);
    }

    /**
     * 说明：查询所有
     */
    public <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz){
        RealmResults<T> results = getRealm().where(clazz).findAll();
        return results;
    }

    /**
     * 说明：通过条件查询所有
     * @param clazz
     * @param where [name,"张三"，age,"13"]
     */
    public <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz,String... where){
        if (where == null || where.length % 2 != 0){
            throw new RuntimeException("RealmUtils---findAll方法中where条件错误！！！");
        }
        RealmQuery<T> query = getRealm().where(clazz);
        for (int i = 0;i < where.length;i+=2){
            query = query.equalTo(where[i],where[i+1]);
            if (i + 2 != where.length){
                query = query.or();
            }
        }
        return query.findAll();
    }

//    /**
//     * 说明：通过条件查询所有
//     * @param clazz
//     * @param where [name]
//     * @param values ["张三","李四"]
//     */
//    public <T extends RealmObject> RealmResults<T> findAll(Class<T> clazz,String where,String... values){
//        if (where == null || StringUtils.isEmpty(where)){
//            throw new RuntimeException("RealmUtils---findAll方法中where条件错误！！！");
//        }
//
//        RealmQuery<T> query = getRealm().where(clazz);
//
//        if (values == null || values.length == 0){
//            return query.findAll();
//        }else {
//            for (int i = 0;i < values.length;i++){
//                query = query.equalTo(where,values[i]);
//                if (i + 1 != values.length){
//                    query = query.or();
//                }
//            }
//            return query.findAll();
//        }
//    }

    /**
     * 说明：查询一条数据
     * @param clazz
     * @param values
     */
    public <T extends RealmObject> T findOne(Class<T> clazz,String... values){
        RealmResults<T> results = findAll(clazz,values);
        T t = null;
        if (results != null && !results.isEmpty()){
            t = results.get(0);
        }
        return t;
    }

}
