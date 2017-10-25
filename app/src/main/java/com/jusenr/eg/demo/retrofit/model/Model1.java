package com.jusenr.eg.demo.retrofit.model;

/**
 * Created by riven_chris on 16/7/4.
 */
public class Model1<T> {
    private T data;
    private int status;
    private int http_code;//纬度后台接口状态码
    private int http_status_code;//日历后台接口状态码
    private String msg;

    protected int event_hits;// 0
    protected long event_id;// 4733114257876108528
    protected int excute_time;// 2

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getHttp_status_code() {
        return http_status_code;
    }

    public void setHttp_status_code(int http_status_code) {
        this.http_status_code = http_status_code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return "Model1{" +
                "data=" + data +
                ", status=" + status +
                ", http_code=" + http_code +
                ", http_status_code=" + http_status_code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
