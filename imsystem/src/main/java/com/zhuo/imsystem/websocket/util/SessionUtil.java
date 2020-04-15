package com.zhuo.imsystem.websocket.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static Map<String, ChannelHandlerContext> onlineUserMap = new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static void userOnline(String uid, Channel channel){
        ChannelContainer.addUserChannel(uid,channel);
    }

    public static void userOffline(String uid){
        onlineUserMap.remove(uid);
    }

    public static ChannelHandlerContext getUserContext(String uid){
        return onlineUserMap.get(uid);
    }
}
