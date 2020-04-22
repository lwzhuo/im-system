package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "im-system",type = "_doc", shards = 5, replicas = 1)
public class Message {
    @Id
    private Long id;

    @Field(type = FieldType.Date)
    private long ts;

    @Field(type = FieldType.Keyword)
    private String channelId;

    @Field(type = FieldType.Keyword)
    private String sendFrom;

    @Field(type = FieldType.Integer)
    private int msgType;

    @Field(type = FieldType.Integer)
    private int channelType;

    @Field(type = FieldType.Text)
    private String msg;

    @Field(type = FieldType.Keyword)
    private String messageId;

    public Message(long ts, String channelId, String sendFrom, int msgType, int channelType, String msg) {
        this.ts = ts;
        this.channelId = channelId;
        this.sendFrom = sendFrom;
        this.msgType = msgType;
        this.channelType = channelType;
        this.msg = msg;
        this.messageId = generateMessageTid();
    }

    public long getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String  messageId) {
        this.messageId = messageId;
    }

    public static String generateMessageTid(){
        long nanots = System.nanoTime();
        long random = (long)Math.random()*10000;
        return nanots+""+random;
    }
}
