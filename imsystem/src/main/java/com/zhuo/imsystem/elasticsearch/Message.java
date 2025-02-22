package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document(indexName = "im-system",type = "_doc", shards = 5, replicas = 0)
public class Message {
    @Id
    private Long id;

    @Field(type = FieldType.Date)
    private long ts;

    @Field(type = FieldType.Keyword)
    private String date;

    @Field(type = FieldType.Keyword)
    private String channelId;

    @Field(type = FieldType.Keyword)
    private String fromUid;

    @Field(type = FieldType.Integer)
    private int msgType;

    @Field(type = FieldType.Integer)
    private int channelType;

    @Field(type = FieldType.Text)
    private String msg;

    @Field(type = FieldType.Keyword)
    private String messageId;

    @Field(type = FieldType.Keyword)
    private int status;

    public Message(){

    }

    public Message(long ts, String channelId, String fromUid, int msgType, int channelType, String msg,String messageId,int status) {
        this.ts = ts;
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.msgType = msgType;
        this.channelType = channelType;
        this.msg = msg;
        this.messageId = messageId;
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = dFormat.format(new Date(ts));
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getfromUid() {
        return fromUid;
    }

    public void setfromUid(String fromUid) {
        this.fromUid = fromUid;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static String generateMessageTid(){
        long nanots = System.nanoTime();
        long random = (long)Math.random()*10000;
        return nanots+""+random;
    }
}
