package com.jusenr.eg.demo.retrofit.model;

import java.io.Serializable;

/**
 * Created by riven_chris on 16/7/14.
 */
public class Model3<T> implements Serializable {

    protected int error_code;
    protected T list;
    protected String msg;

    protected int event_hits;// 0
    protected long event_id;// 4733114257876108528
    protected int excute_time;// 2

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getEvent_hits() {
        return event_hits;
    }

    public void setEvent_hits(int event_hits) {
        this.event_hits = event_hits;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public int getExcute_time() {
        return excute_time;
    }

    public void setExcute_time(int excute_time) {
        this.excute_time = excute_time;
    }

    @Override
    public String toString() {
        return "Model3{" +
                "error_code=" + error_code +
                ", list=" + list +
                ", msg='" + msg + '\'' +
                '}';
    }
}
