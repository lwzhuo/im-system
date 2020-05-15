package com.zhuo.imsystem.websocket.util;

import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

// 用户会话管理 (主要是channel和channel容器操作)
public class SessionUtil {
    private static Logger logger = LoggerFactory.getLogger(SessionUtil.class);
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
        // todo 返回结果优化
        if(channel!=null){
            logger.info("用户["+uid+"] 绑定到群组["+groupChannelId+"]");
            return channelGroup.add(channel);
        }
        else
            return false;
    }

    public static boolean unbindFromChannelGroup(String uid,String groupChannelId){
        ChannelGroup channelGroup = ChannelContainer.getChannelGroupByChannelId(groupChannelId);
        Channel channel = ChannelContainer.getChannelByUserId(uid);
        // todo 返回结果优化
        if(channel!=null)
            return channelGroup.remove(channel);
        else
            return false;
    }

    public void ChannelGroupInit(){
        logger.info("开始ChannelGroup初始化");
        // 获取所有的channelId
        List<String> groupChannelIds = channelMapper.queryAllGroupChannelIds();
        for(String channelId : groupChannelIds){
            ChannelGroup channelGroup = ChannelContainer.createChannelGroup();
            ChannelContainer.addChannelGroup(channelId,channelGroup);
        }
        logger.info("ChannelGroup初始化完毕");
    }
}
