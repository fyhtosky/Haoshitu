package com.sj.yinjiaoyun.shituwang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/6/27.
 */
public class Children  implements Serializable{
    private int id;
    private String code;
    private String name;
    private String parentCode;
    private int level;
    private String orderBy;
    private String isDefault;
    private String note;
    private List<Children> children;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public String getParentCode() {
        return parentCode;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getOrderBy() {
        return orderBy;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    public String getIsDefault() {
        return isDefault;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }
    public List<Children> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Children{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
