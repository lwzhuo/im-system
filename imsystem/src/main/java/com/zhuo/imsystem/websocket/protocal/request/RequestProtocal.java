package com.zhuo.imsystem.websocket.protocal.request;

import com.zhuo.imsystem.websocket.protocal.Protocal;

public class RequestProtocal extends Protocal {
    protected int action;     // 动作
    protected String msg;     // 消息
    protected String fromUid; // 来源uid
    protected int msgType; // 消息类型(文本/图片/表情)
    protected long ts;         // 发生的时间戳
    protected int statusCode; // 状态码
    protected String jsonString; // json格式字符串
    protected String channelId; // channel标识符
    protected int channelType;//channel类型(私聊/群聊/系统channel)

    public RequestProtocal(){
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

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }
}
