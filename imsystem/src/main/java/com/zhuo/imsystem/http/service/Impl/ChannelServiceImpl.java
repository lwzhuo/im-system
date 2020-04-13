package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.config.StatusCode;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.util.CommonException;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    ChannelMemberMapper channelMemberMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public ChannelDto createChannel(ChannelDto channelDto) throws Exception{
        // 校验channel类型
        int channelType = channelDto.getChannelType();
        if(channelType!= Const.PRIVATE_CHANNEL||channelType!=Const.GROUP_CHALLEL){
            System.out.println("channel类型错误");
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
        }
        // 校验chennel 创建者用户
        String creatorId = channelDto.getCreatorId();
        User user = userMapper.queryUser(creatorId);
        if(user==null){
            System.out.println("channel 创建者用户不存在");
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
        }

        // 校验channel 成员用户
        ArrayList<ChannelMemberDto> channelMemberList = channelDto.getChannelUserList();
        for(ChannelMemberDto channelMemberDto:channelMemberList){
            // todo 部分参数如创建时间 退出时间改为由服务端生成
            String uid = channelMemberDto.getUid();
            User res = userMapper.queryUser(uid);
            if(res==null) {
                System.out.println("channel 成员用户不存在");
                throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
            }
        }

        if(channelType==Const.GROUP_CHALLEL){
            //todo 群组channel的创建
        }

        String channelId = UUID.randomUUID().toString().replace("-", "");
        channelDto.setChannelId(channelId);
        boolean channelRes = channelMapper.saveChannel(channelDto);
        boolean channelMemberRes = channelMemberMapper.saveChannelMember(channelDto.getChannelUserList());
        if(channelRes&&channelMemberRes)
            return channelDto;
        else
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
    }
}
