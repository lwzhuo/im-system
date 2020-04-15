package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.websocket.protocal.Protocal;

public class ResponseProtocal extends Protocal {
    private int action;     // 动作
    private String msg;     // 消息
    private String fromUid; // 来源uid
    private int msgType; // 消息类型
    private long ts;         // 发生的时间戳
    private int statusCode; // 状态码

    public ResponseProtocal(){
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

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
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
}
