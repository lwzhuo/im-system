package com.zhuo.imsystem.websocket.protocal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

// 协议映射字典
public class ProtocalMap extends HashMap<Integer,Class>{
    private static ProtocalMap map = new ProtocalMap();
    static {
        map.put(1,EchoProtocal.class);
        map.put(2,SingleChatProtocal.class);
    }
    private ProtocalMap(){
    }
    public static HashMap<Integer,Class> getMap(){
        return map;
    }

    public static void main(String[] args){
        System.out.println(ProtocalMap.getMap());

        JSONObject jsonObject = null;
        System.out.println("解析协议");
        jsonObject = JSONObject.parseObject("{\"type\":1,\"msg\":\"test\",\"uid\":\"1\",\"to_uid\":\"1\",\"ts\":1585464517108}");
        int type = jsonObject.getIntValue("type");// 获取数据类型
        EchoProtocal protocal = (EchoProtocal) JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(type));// 获取pojo
        System.out.println(protocal.getMsg());
    }
}

