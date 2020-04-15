package com.zhuo.imsystem.websocket.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 用户会话管理 (主要是channel和channel容器操作)
public class SessionUtil {
    public static void userOnline(String uid, Channel channel){
        ChannelContainer.addUserChannel(uid,channel);
    }

}
