package com.zhuo.imsystem.websocket.util;

import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.context.ApplicationContext;

import java.util.List;

// 用户会话管理 (主要是channel和channel容器操作)
public class SessionUtil {
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private ChannelMapper channelMapper = applicationContext.getBean(ChannelMapper.class);
    private ChannelMemberMapper channelMemberMapper = applicationContext.getBean(ChannelMemberMapper.class);

    public static void userOnline(String uid, Channel channel){
        ChannelContainer.addUserChannel(uid,channel);
    }

    public static void userOffline(String uid){
        ChannelContainer.removeUserChannel(uid);
    }

    public static boolean isUserOnline(String uid) {
        Channel channel = ChannelContainer.getChannelByUserId(uid);
        if(channel==null)
            return false;
        else
            return true;
    }

    public static boolean bindToChannelGroup(String uid,String groupChannelId){
        ChannelGroup channelGroup = ChannelContainer.getChannelGroupByChannelId(groupChannelId);
        Channel channel = ChannelContainer.getChannelByUserId(uid);
        boolean res = channelGroup.add(channel);
        return res;
    }

    public static boolean unbindFromChannelGroup(String uid,String groupChannelId){
        ChannelGroup channelGroup = ChannelContainer.getChannelGroupByChannelId(groupChannelId);
        Channel channel = ChannelContainer.getChannelByUserId(uid);
        boolean res = channelGroup.remove(channel);
        return res;
    }

    public void ChannelGroupInit(){
        System.out.println("开始ChannelGroup初始化");
        // 获取所有的channelId
        List<String> groupChannelIds = channelMapper.queryAllGroupChannelIds();
        for(String channelId : groupChannelIds){
            ChannelGroup channelGroup = ChannelContainer.createChannelGroup();
            ChannelContainer.addChannelGroup(channelId,channelGroup);
        }
        System.out.println("ChannelGroup初始化完毕");
    }
}
