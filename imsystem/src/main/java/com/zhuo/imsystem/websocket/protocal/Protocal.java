package com.zhuo.imsystem.websocket.protocal;

public class Protocal {
    private int action;     // 动作
    private String msg;     // 消息内容
    private long ts;         // 发生的时间戳

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
}
