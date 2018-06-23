package com.sj.yinjiaoyun.shituwang.bean;

/**
 * Created by ${沈军 961784535@qq.com} on 2017/9/18.
 */
public class VersionCheckBean {

    /**
     * status : 200
     * msg : null
     * data : null
     * map : {"url":"http://139.196.255.175:8200/statics/android_app/haoshitu.apk","version":"1"}
     * ok : true
     */

    private int status;
    private Object msg;
    private Object data;
    private MapBean map;
    private boolean ok;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static class MapBean {
        /**
         * url : http://139.196.255.175:8200/statics/android_app/haoshitu.apk
         * version : 1
         */

        private String url;
        private String version;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
