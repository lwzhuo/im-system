package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.Channel;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.util.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService {
    // channel类型
    private static int PRIVATE_CHANNEL = 1;
    private static int GROUP_CHANNEL = 2;
    // 用户类型
    private static int CREATOR = 1;
    private static int ATTENDER = 2;
    // 用户状态
    private static int IN_CHANNEL = 1;
    private static int LEFT_CHANNEL = 2;

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    ChannelMemberMapper channelMemberMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserChannelService userChannelService;

    @Override
    public ChannelDto createChannel(ChannelDto channelDto) throws Exception{
        // 校验channel类型是否合法
        int channelType = channelDto.getChannelType();
        if(channelType!= PRIVATE_CHANNEL&&channelType!=GROUP_CHANNEL){
            System.out.println("channel类型错误");
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
        }

        // 校验群聊名称是否存在
        if(channelType==GROUP_CHANNEL){
            if(channelDto.getChannelName()==null || channelDto.getChannelName().trim().equals("")){
                System.out.println("群聊名称为空");
                throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"群聊名称不能为空");
            }
        }

        // 生成channelid
        String channelId = UUID.randomUUID().toString().replace("-", "");
        channelDto.setChannelId(channelId);

        // 校验chennel 创建者用户是否合法
        String creatorId = channelDto.getCreatorId();
        User creator = userMapper.queryUser(creatorId);
        if(creator==null){
            System.out.println("channel 创建者用户不存在");
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
        }

        Date now = new Date();
        List<ChannelMemberDto> channelMemberList = channelDto.getChannelUserList();
        if(channelType==Const.GROUP_CHALLEL){
            // 校验channel 成员用户是否合法 补充相应的参数
            for(ChannelMemberDto channelMemberDto:channelMemberList){
                String uid = channelMemberDto.getUid();
                User res = userMapper.queryUser(uid);
                if(res==null) {
                    System.out.println("channel 成员用户不存在");
                    throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
                }else {
                    // 补充参数
                    channelMemberDto.setChannelId(channelId);
                    channelMemberDto.setUid(uid);
                    channelMemberDto.setJoinTime(now);
                    channelMemberDto.setChannelType(channelType);
                    channelMemberDto.setStatus(IN_CHANNEL);
                    channelMemberDto.setUpdateTime(now);
                    channelMemberDto.setCtime(now);
                    // 处理创建者的信息
                    if(uid.equals(creatorId)){
                        channelMemberDto.setUserType(CREATOR);
                    }else {
                        channelMemberDto.setUserType(ATTENDER);
                    }
                }
            }
            // 保存channel数据
            channelDto.setCtime(now);
            channelDto.setUpdateTime(now);
            channelMapper.saveChannel(channelDto);
            for(ChannelMemberDto item: channelMemberList){
                channelMemberMapper.saveChannelMember(item);
            }
        }else if(channelType==Const.PRIVATE_CHANNEL){
            // 私聊中 两个成员互为创建者 共享同一个channel
            // 校验成员信息是否合法
            String creatorUid = channelDto.getCreatorId();
            String memberUid = channelDto.getAttenderId();
            User member = userMapper.queryUser(memberUid);
            String creatorName = creator.getUserName();
            String memberName = member.getUserName();
            if(member==null) {
                System.out.println("channel 成员用户不存在");
                throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
            }
            // 检查channel是否已经创建过
            ChannelDto res = channelMapper.queryPrivateChannelByMemberUid(creatorUid,memberUid);
            if(res!=null){
                // 根据用户uid 查询到相应的channel信息 直接返回这个channel数据
                return res;
            }else {
                // 新增channel信息
                ChannelDto creatorA = new ChannelDto(channelId,memberName,creatorId,memberUid,PRIVATE_CHANNEL,now,now);
                ChannelDto creatorB = new ChannelDto(channelId,creatorName,memberUid,creatorId,PRIVATE_CHANNEL,now,now);
                channelMapper.saveChannel(creatorA);
                channelMapper.saveChannel(creatorB);
                // 新增成员信息
                ChannelMemberDto memberA = new ChannelMemberDto(creatorId,channelId,now,channelType,ATTENDER,IN_CHANNEL,now,now);// 成员A dto
                ChannelMemberDto memberB = new ChannelMemberDto(memberUid,channelId,now,channelType,ATTENDER,IN_CHANNEL,now,now);// 成员B dto
                channelMemberMapper.saveChannelMember(memberA);
                channelMemberMapper.saveChannelMember(memberB);

                return creatorA;
            }
        }
        return channelDto;
    }

    // 获取channel信息
    public ChannelDto getInfo(String channelId,String uid) throws Exception{
        List<ChannelDto> channelDtoList = channelMapper.queryChannelInfoByChannelId(channelId);
        ChannelDto res = null;
        if(channelDtoList==null || channelDtoList.size()==0){
            return res;
        }
        int channelType = channelDtoList.get(0).getChannelType();
        if(channelType==PRIVATE_CHANNEL){
            // 私聊
            if(channelDtoList.size()!=2)
                return res;
            if(channelDtoList.get(0).getCreatorId().equals(uid)) {
                ChannelDto item = channelDtoList.get(0);
                item.setAttenderName(item.getChannelName());
                item.setChannelUserList(userChannelService.getChannelMemberList(channelId));
                return item;
            }else{
                ChannelDto item = channelDtoList.get(1);
                item.setAttenderName(item.getChannelName());
                item.setChannelUserList(userChannelService.getChannelMemberList(channelId));
                return item;
            }
        }else {
            //群聊
            ChannelDto channelDto = channelDtoList.get(0);
            channelDto.setChannelUserList(userChannelService.getChannelMemberList(channelId));
            return channelDto;
        }
    }

    // 判断是否为管理员
    public boolean isAdmin(String uid,String channelId) throws Exception{
        List<ChannelDto> channelDtoList =  channelMapper.queryChannelInfoByChannelId(channelId);
        boolean res = false;
        for(ChannelDto item:channelDtoList){
            if(item.getCreatorId().equals(uid)){
                res = true;
                break;
            }
        }
        return res;
    }

}
