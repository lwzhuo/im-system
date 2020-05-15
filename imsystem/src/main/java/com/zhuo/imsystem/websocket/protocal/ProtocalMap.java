package com.zhuo.imsystem.websocket.protocal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.websocket.protocal.request.*;
import com.zhuo.imsystem.websocket.protocal.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

// 协议映射字典
public class ProtocalMap extends HashMap<Integer,Class>{
    private static Logger logger = LoggerFactory.getLogger(ProtocalMap.class);
    // echo 协议
    public static final int Echo_Request = 1;
    public static final int Echo_Response = 2;

    // ws注册协议
    public static final int Register_Request = 3;
    public static final int Register_Response = 4;

    // 新消息协议
    public static final int New_message_Request = 5;
    public static final int New_message_Response = 6;

    // 用户绑定群组协议
    public static final int Bind_to_group_channel_Request = 7;
    public static final int Bind_to_group_channel_Response = 8;

    // channel创建协议
    public static final int Channel_create_Request = 9;
    public static final int Channel_create_Response = 10;

    // 用户加入channel协议
    public static final int Member_join_channel_Request = 11;
    public static final int Member_join_channel_Response = 12;

    // 用户离开channel协议
    public static final int Member_left_channel_Request = 13;
    public static final int Member_left_channel_Response = 14;

    private static ProtocalMap map = new ProtocalMap();
    static {
        map.put(Echo_Request, EchoRequestProtocal.class);
        map.put(Echo_Response, EchoResponseProtocal.class);

        map.put(Register_Request, RegisterRequestProtocal.class);
        map.put(Register_Response, RegisterResponseProtocal.class);

        map.put(New_message_Request, NewMessageRequestProtocal.class);
        map.put(New_message_Response, NewMessageResponseProtocal.class);

        map.put(Bind_to_group_channel_Request, BindToGroupRequestProtocal.class);
        map.put(Bind_to_group_channel_Response, BindToGroupResponseProtocal.class);

        map.put(Channel_create_Request, ChannelCreateRequestProtocal.class);
        map.put(Channel_create_Response, ChannelCreateResponseProtocal.class);

        map.put(Member_join_channel_Request, MemberJoinRequestProtocal.class);
        map.put(Member_join_channel_Response, MemberJoinResponseProtocal.class);

        map.put(Member_left_channel_Request, MemberLeftRequestProtocal.class);
        map.put(Member_left_channel_Response, MemberLeftResponseProtocal.class);
    }
    private ProtocalMap(){
    }
    public static HashMap<Integer,Class> getMap(){
        return map;
    }

    public static Protocal toProtocalObject(String jsonString){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        int type = jsonObject.getIntValue("action");// 获取数据类型
        Protocal protocal = (Protocal) JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(type));// 获取pojo
        return protocal;
    }

    public static String toJSONString(Protocal protocal){
        return JSONObject.toJSON(protocal).toString();
    }

    public static void main(String[] args){
        String jsonString = "{\"action\":1,\"msg\":\"test\",\"fromUid\":\"1\",\"ts\":1585464517108}";

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        int type = jsonObject.getIntValue("action");// 获取数据类型
        EchoRequestProtocal protocal = (EchoRequestProtocal) JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(type));// 获取pojo
        logger.info(protocal.getMsg());

        String str = JSONObject.toJSON(protocal).toString();
        logger.info(str);

        logger.info("=============================================================================");

        Protocal protocal1 = ProtocalMap.toProtocalObject(jsonString);
        logger.info(protocal1.getMsg());

        logger.info(ProtocalMap.toJSONString(protocal));
    }
}

