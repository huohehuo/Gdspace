package com.example.lins.gdspace.bean;

import java.util.List;

/**
 * Created by LINS on 2017/2/22.
 */

public class MainBean {


    /**
     * code : 0
     * error :
     * body : {"datalist":[{"title":"美食城","lon":"30.679879","lat":"104.064855","world":"中国第一火锅"},{"title":"绝味火火","lon":"31.238068","lat":"121.501654","world":"宇宙第一川味"},{"title":"老友粉","lon":"34.341568","lat":"108.940174","world":"微辣最为合适"},{"title":"游乐场","lon":"34.7466","lat":"113.625367","world":"绝不出意外儿童娱乐场所"}]}
     */

    private String code;
    private String error;
    private BodyBean body;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private List<DatalistBean> datalist;

        public List<DatalistBean> getDatalist() {
            return datalist;
        }

        public void setDatalist(List<DatalistBean> datalist) {
            this.datalist = datalist;
        }

        public static class DatalistBean {
            /**
             * title : 美食城
             * lon : 30.679879
             * lat : 104.064855
             * world : 中国第一火锅
             */

            private String title;
            private String lon;
            private String lat;
            private String world;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getWorld() {
                return world;
            }

            public void setWorld(String world) {
                this.world = world;
            }
        }
    }
}
