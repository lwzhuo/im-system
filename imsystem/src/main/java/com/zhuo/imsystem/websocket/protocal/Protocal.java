package com.zhuo.imsystem.websocket.protocal;

public class Protocal {
    private int action;     // 动作
    private String msg;     // 消息内容
    private long ts;         // 发生的时间戳
    private int msgType; // 消息类型
    private int statusCode; // 状态码

    public Protocal(){
        this.ts = System.currentTimeMillis();
    }
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Protocal success(String msg){
        this.msg = msg;
        return this;
    }

    public Protocal success(int statusCode,String msg){
        this.statusCode = statusCode;
        this.msg = msg;
        return this;
    }

    public Protocal error(int statusCode,String msg){
        this.statusCode = statusCode;
        this.msg = msg;
        return this;
    }
}
