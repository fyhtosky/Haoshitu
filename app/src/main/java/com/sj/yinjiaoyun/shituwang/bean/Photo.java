package com.sj.yinjiaoyun.shituwang.bean;

import java.io.Serializable;

/**
 * Created by wanzhiying on 2017/3/16.
 * 照片的实体
 */
public class Photo  implements Serializable {
    private int id;
    private String path;  //路径
    private boolean isCamera;

    public Photo(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isCamera() {
        return isCamera;
    }

    public void setIsCamera(boolean isCamera) {
        this.isCamera = isCamera;
    }
}
