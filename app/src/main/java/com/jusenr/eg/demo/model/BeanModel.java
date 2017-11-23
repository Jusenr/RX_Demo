package com.jusenr.eg.demo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/23
 * Time       : 10:39
 * Project    ：RX_Demo.
 */
public class BeanModel implements Serializable {

    /**
     * error_code : 0
     * error_msg :
     * data : []
     */

    private int error_code;
    private String error_msg;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
