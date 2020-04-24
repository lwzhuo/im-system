package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.http.service.UserChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userChannelService")
public class UserChannelServiceImpl implements UserChannelService {
    @Autowired
    ChannelMemberMapper channelMemberMapper;

    @Autowired
    ChannelMapper channelMapper;

    // 检查用户是否在channel中
    public ChannelMemberDto getMemberChannel(String channelId,String uid) throws Exception{
        ChannelMemberDto channelMemberDto = channelMemberMapper.getChannelMember(channelId,uid);
        if(channelMemberDto==null)
            return null;
        else
            return channelMemberDto;
    }

    public List<ChannelDto> getUserChannelList(String uid) throws Exception{
        List<String> channelIdList = channelMapper.queryChannelIdsByMemberUid(uid);
        List<ChannelDto> res = new ArrayList<ChannelDto>();
        for(String channelId : channelIdList){
            List<ChannelDto> channelDtoList = channelMapper.queryChannelInfoByChannelId(channelId);
            if(channelDtoList.size()==1 && channelDtoList.get(0).getChannelType()== ConstVar.GROUP_CHALLEL) // 群聊
                res.add(channelDtoList.get(0));
            else if(channelDtoList.size()==2){// 私聊 处理可能查出来两个channel的情况
                if(channelDtoList.get(0).getCreatorId().equals(uid))
                    res.add(channelDtoList.get(0));
                else if(channelDtoList.get(1).getCreatorId().equals(uid))
                    res.add(channelDtoList.get(1));
            }
        }
        return res;
    }
}
