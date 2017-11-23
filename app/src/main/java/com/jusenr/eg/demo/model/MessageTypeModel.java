package com.jusenr.eg.demo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/23
 * Time       : 9:26
 * Project    ：RX_Demo.
 */
public class MessageTypeModel implements Serializable {

    /**
     * data : [{"desc":"处理孩子管理权限请求","id":1,"title":"权限请求"},{"desc":"定期接收孩子使用平板的数据统计报告","id":2,"title":"成长报告"},{"desc":"来自系统的消息通知","id":3,"title":"系统通知"},{"desc":"21天让孩子养成良好的学习习惯","id":4,"title":"21天计划"}]
     * error_code : 0
     * error_msg :
     */

    private int error_code;
    private String error_msg;
    private List<DataBean> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * desc : 处理孩子管理权限请求
         * id : 1
         * title : 权限请求
         */

        private String desc;
        private int id;
        private String title;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
