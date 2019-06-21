package com.heyongrui.base.assist;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.heyongrui.base.base.BaseStatic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;


public class AppData {

    private SPUtils spUtils;

    public AppData() {
        spUtils = SPUtils.getInstance(BaseStatic.APP_DATA_SP_NAME);
    }

    public void clearTarget(Context context, String targetKey) {
        if (isExistDataCache(targetKey, context)) {
            deleteObject(targetKey, context);
        }
        if (spUtils.contains(targetKey)) {
            spUtils.remove(targetKey);
        }
    }

    public void clearAppData(Context context) {
        spUtils.clear();
        deleteObject(BaseStatic.APP_DATA_NEWS, context);
    }

    public void saveNewsData(Context context, List<Object> newsDtoList) {
        if (newsDtoList != null) {
            saveObject((Serializable) newsDtoList, BaseStatic.APP_DATA_NEWS, context);
        }
    }

    public List<Object> getNewsData(Context context) {
        List<Object> newsDtoList = null;
        if (isExistDataCache(BaseStatic.APP_DATA_NEWS, context)) {
            newsDtoList = (List<Object>) readObject(BaseStatic.APP_DATA_NEWS, context);
        }
        return newsDtoList;
    }

    public void setBaseUrl(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) return;
        spUtils.put(BaseStatic.BASE_URL_SP_KEY, baseUrl);
    }

    public String getBaseUrl() {
        return spUtils.getString(BaseStatic.BASE_URL_SP_KEY);
    }

    /**
     * 判断缓存数据是否可读
     */

    public boolean isReadDataCache(String cachefile, Context context) {
        if (TextUtils.isEmpty(cachefile))
            return false;
        return readObject(cachefile, context) != null;
    }

    /**
     * 判断缓存是否存在
     */
    public boolean isExistDataCache(String cachefile, Context context) {
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data == null) {
            exist = false;
        } else {
            if (data.exists())
                exist = true;
        }
        return exist;
    }


    /**
     * 保存对象
     */
    public boolean saveObject(Serializable ser, String file, Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 读取对象
     */
    public Serializable readObject(String file, Context context) {
        if (!isExistDataCache(file, context))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 删除对象
     */
    public void deleteObject(String file, Context context) {
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            data.delete();
        }
    }
}
