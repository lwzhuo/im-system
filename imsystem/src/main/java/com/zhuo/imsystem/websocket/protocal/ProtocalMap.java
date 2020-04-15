package com.zhuo.imsystem.websocket.protocal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.websocket.protocal.request.EchoRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.request.RegisterRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.EchoResponseProtocal;
import com.zhuo.imsystem.websocket.protocal.response.RegisterResponseProtocal;

import java.util.HashMap;

// 协议映射字典
public class ProtocalMap extends HashMap<Integer,Class>{
    private static ProtocalMap map = new ProtocalMap();
    static {
        map.put(1, EchoRequestProtocal.class);
        map.put(2, EchoResponseProtocal.class);

        map.put(3, RegisterRequestProtocal.class);
        map.put(4, RegisterResponseProtocal.class);
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
        System.out.println(ProtocalMap.getMap());
        String jsonString = "{\"action\":1,\"msg\":\"test\",\"fromUid\":\"1\",\"ts\":1585464517108}";

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        int type = jsonObject.getIntValue("action");// 获取数据类型
        EchoRequestProtocal protocal = (EchoRequestProtocal) JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(type));// 获取pojo
        System.out.println(protocal.getMsg());

        String str = JSONObject.toJSON(protocal).toString();
        System.out.println(str);

        System.out.println("=============================================================================");

        Protocal protocal1 = ProtocalMap.toProtocalObject(jsonString);
        System.out.println(protocal1.getAction());
        System.out.println(protocal1.getMsg());

        System.out.println(ProtocalMap.toJSONString(protocal));
    }
}

