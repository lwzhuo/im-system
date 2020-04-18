package com.zhuo.imsystem.queue.model.message;

// 队列数据载体
public class BlockingQueueMessage {
    private int action;
    private String msg;

    public BlockingQueueMessage(int action, String msg){
        this.action = action;
        this.msg = msg;
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
}
