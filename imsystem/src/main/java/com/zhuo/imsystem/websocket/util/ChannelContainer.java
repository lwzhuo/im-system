package com.zhuo.imsystem.websocket.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import java.util.concurrent.ConcurrentHashMap;

// channel容器
public class ChannelContainer {
    // 注：channel的id是项目自定义生成的，可以长期保存的id。而不是使用netty中Channel.id()中的id值

    // todo CHANNELID_CHANNEL_MAP 和 CHANNEL_CHANNELID_MAP 具体使用方式待定
    //记录channel的id和channel对象的映射关系
    public static ConcurrentHashMap<String, Channel> CHANNELID_CHANNEL_MAP = new ConcurrentHashMap<String ,Channel>();

    //记录channel和channel id的映射关系
    public static ConcurrentHashMap<Channel,String> CHANNEL_CHANNELID_MAP = new ConcurrentHashMap<>();

    //记录用户的uid和channel的映射关系 （用于私聊或者定向推送）
    public static ConcurrentHashMap<String,Channel> USERID_CHANNEL_MAP = new ConcurrentHashMap<String, Channel>();

    // 群组channel id和ChannelGroup的映射关系 （以频道为维度进行群聊或者全体系统推送 ）
    private static final ConcurrentHashMap<String, ChannelGroup> CHANNELID_CHANNELGROUPS_MAP = new ConcurrentHashMap<>();

    // 用户uid和群组channelid集合的关系 (用于根据用户查找其相关的channelID 以用户为维度 进行和该用户相关的频道推送 用户状态、头像昵称等信息)
    private static final ConcurrentHashMap<String, ChannelGroup> USER_CHANNELSET_MAP = new ConcurrentHashMap<String, ChannelGroup>();

    public static Channel getChannelByChannelId(String channelId){
        return CHANNELID_CHANNEL_MAP.get(channelId);
    }

    public static String getChannelIdByChannel(Channel channel){
        return CHANNEL_CHANNELID_MAP.get(channel);
    }

    public static void addChannel(String channelId,Channel channel){
        CHANNELID_CHANNEL_MAP.put(channelId,channel);
        CHANNEL_CHANNELID_MAP.put(channel,channelId);
        return;
    }

    public static Channel getChannelByUserId(String uid){
        return USERID_CHANNEL_MAP.get(uid);
    }

    public static void addUserChannel(String uid,Channel channel){
        USERID_CHANNEL_MAP.put(uid,channel);
        return;
    }

    public static void removeUserChannel(String uid){
        USERID_CHANNEL_MAP.remove(uid);
    }
}
