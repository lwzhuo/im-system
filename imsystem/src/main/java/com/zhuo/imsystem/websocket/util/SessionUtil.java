package com.zhuo.imsystem.websocket.util;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static Map<String, ChannelHandlerContext> onlineUserMap = new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static void userOnline(String uid,ChannelHandlerContext ctx){
        onlineUserMap.put(uid,ctx);
    }

    public static void userOffline(String uid){
        onlineUserMap.remove(uid);
    }

    public static ChannelHandlerContext getUserContext(String uid){
        return onlineUserMap.get(uid);
    }
}
