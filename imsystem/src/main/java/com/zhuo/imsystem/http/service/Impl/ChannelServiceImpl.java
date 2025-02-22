package com.zhuo.imsystem.http.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.http.mapper.PublicChannelDictMapper;
import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.util.CommonException;
import com.zhuo.imsystem.queue.producer.BlockingQueueProvider;
import com.zhuo.imsystem.websocket.protocal.request.ChannelCreateRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.request.MemberJoinRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.request.MemberLeftRequestProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService {
    private static Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    ChannelMemberMapper channelMemberMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserChannelService userChannelService;

    @Autowired
    PublicChannelDictMapper publicChannelDictMapper;


    @Override
    public ChannelDto createChannel(ChannelDto channelDto) throws Exception{
        // 校验channel类型是否合法
        int channelType = channelDto.getChannelType();
        String channelName = channelDto.getChannelName();
        if(channelType!= ConstVar.PRIVATE_CHANNEL&&channelType!=ConstVar.GROUP_CHANNEL&&channelType!=ConstVar.PUBLIC_CHANNEL){
            logger.info("channel类型错误");
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
        }

        // 校验群聊名称是否存在
        if(channelType==ConstVar.GROUP_CHANNEL || channelType==ConstVar.PUBLIC_CHANNEL){
            if(channelDto.getChannelName()==null || channelDto.getChannelName().trim().equals("")){
                logger.info("群聊名称为空");
                throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"群聊名称不能为空");
            }
        }

        // 生成channelid
        String channelId = UUID.randomUUID().toString().replace("-", "");
        channelDto.setChannelId(channelId);

        // 对于公开群组 直接创建
        if(channelType==ConstVar.PUBLIC_CHANNEL){
            channelMapper.saveChannel(channelDto);
            // 创建ChannelGroup对象
            ChannelGroup channelGroup = ChannelContainer.createChannelGroup();
            ChannelContainer.addChannelGroup(channelId,channelGroup);
            return channelDto;
        }
        // 校验chennel 创建者用户是否合法
        String creatorId = channelDto.getCreatorId();
        User creator = userMapper.queryUser(creatorId);
        if(creator==null){
            logger.info("channel 创建者用户不存在");
            throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
        }

        Date now = new Date();
        List<ChannelMemberDto> channelMemberList = channelDto.getChannelUserList();
        if(channelType== ConstVar.GROUP_CHANNEL){
            // 校验channel 成员用户是否合法 补充相应的参数
            for(ChannelMemberDto channelMemberDto:channelMemberList){
                String uid = channelMemberDto.getUid();
                User res = userMapper.queryUser(uid);
                if(res==null) {
                    logger.info("channel 成员用户不存在");
                    throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
                }else {
                    // 补充参数
                    channelMemberDto.setChannelId(channelId);
                    channelMemberDto.setUid(uid);
                    channelMemberDto.setJoinTime(now);
                    channelMemberDto.setChannelType(channelType);
                    channelMemberDto.setStatus(ConstVar.IN_CHANNEL);
                    channelMemberDto.setUpdateTime(now);
                    channelMemberDto.setCtime(now);
                    // 处理创建者的信息
                    if(uid.equals(creatorId)){
                        channelMemberDto.setUserType(ConstVar.CREATOR);
                    }else {
                        channelMemberDto.setUserType(ConstVar.ATTENDER);
                    }
                }
            }
            // 创建ChannelGroup对象
            ChannelGroup channelGroup = ChannelContainer.createChannelGroup();
            ChannelContainer.addChannelGroup(channelId,channelGroup);
            // 保存channel数据
            channelDto.setCtime(now);
            channelDto.setUpdateTime(now);
            channelMapper.saveChannel(channelDto);
            for(ChannelMemberDto item: channelMemberList){
                channelMemberMapper.saveChannelMember(item); // 保存数据
                // 将用户个人channel加入到ChannegGroup中
                Channel channel = ChannelContainer.getChannelByUserId(item.getUid());
                if(channel==null)
                    continue;
                channelGroup.add(channel);
            }
        }else if(channelType==ConstVar.PRIVATE_CHANNEL){
            // 私聊中 两个成员互为创建者 共享同一个channel
            // 校验成员信息是否合法
            String creatorUid = channelDto.getCreatorId();
            String memberUid = channelDto.getAttenderId();
            User member = userMapper.queryUser(memberUid);
            String creatorName = creator.getUserName();
            String memberName = member.getUserName();
            channelName = creatorName; // 私聊情况下 设置channelName
            if(member==null) {
                logger.info("channel 成员用户不存在");
                throw new CommonException(StatusCode.ERROR_CHANNEL_CREATE_FAIL,"channel 创建失败");
            }
            // 检查channel是否已经创建过
            ChannelDto res = channelMapper.queryPrivateChannelByMemberUid(creatorUid,memberUid);
            if(res!=null){
                // 根据用户uid 查询到相应的channel信息 直接返回这个channel数据
                return res;
            }else {
                // 新增channel信息
                ChannelDto creatorA = new ChannelDto(channelId,memberName,creatorId,memberUid,ConstVar.PRIVATE_CHANNEL,now,now);
                ChannelDto creatorB = new ChannelDto(channelId,creatorName,memberUid,creatorId,ConstVar.PRIVATE_CHANNEL,now,now);
                channelMapper.saveChannel(creatorA);
                channelMapper.saveChannel(creatorB);
                // 新增成员信息
                ChannelMemberDto memberA = new ChannelMemberDto(creatorId,channelId,now,channelType,ConstVar.ATTENDER,ConstVar.IN_CHANNEL,now,now);// 成员A dto
                ChannelMemberDto memberB = new ChannelMemberDto(memberUid,channelId,now,channelType,ConstVar.ATTENDER,ConstVar.IN_CHANNEL,now,now);// 成员B dto
                channelMemberMapper.saveChannelMember(memberA);
                channelMemberMapper.saveChannelMember(memberB);

                channelDto =  creatorA;
            }
        }
        // 发送消息通知成员
        sendChannelCreateMessage(channelId,creatorId,channelType,channelName);
        return channelDto;
    }

    // 获取channel信息
    public ChannelDto getInfo(String channelId,String uid) throws Exception{
        // todo 对于链接分享进入的用户 需要重新考虑如何 判断用户是否有权限访问该channel
//        ChannelMemberDto channelMemberDto = userChannelService.getMemberChannel(channelId,uid);
//        if(channelMemberDto==null){
//            throw new CommonException(StatusCode.ERROR_CHANNEL_AUTH_FAILED,"用户没有该房间的的权限");
//        }
        List<ChannelDto> channelDtoList = channelMapper.queryChannelInfoByChannelId(channelId);
        ChannelDto res = null;
        if(channelDtoList==null || channelDtoList.size()==0){
            return res;
        }
        int channelType = channelDtoList.get(0).getChannelType();
        if(channelType==ConstVar.PRIVATE_CHANNEL){
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
            List<ChannelMemberDto> channelUserList = userChannelService.getChannelMemberList(channelId);
            channelDto.setChannelUserList(channelUserList);
            channelDto.setMemberCount(channelUserList.size());
            return channelDto;
        }
    }

    // 判断是否为管理员
    public boolean isAdmin(String uid,String channelId) throws Exception{
        ChannelMemberDto channelMemberDto =  channelMemberMapper.getAdminMemberInfo(channelId);
        if(channelMemberDto==null)
            return false;
        return channelMemberDto.getUid().equals(uid);
    }

    // 检查是否为群聊channel
    public boolean isGroupChannel(String channelId){
        List<ChannelDto> channelDto = channelMapper.queryChannelInfoByChannelId(channelId);
        if(channelDto.size()>0 && channelDto.get(0).getChannelType()==ConstVar.GROUP_CHANNEL)
            return true;
        else
            return false;
    }

    public boolean isPublicChannel(String channelId){
        List<ChannelDto> channelDto = channelMapper.queryChannelInfoByChannelId(channelId);
        if(channelDto.size()>0 && channelDto.get(0).getChannelType()==ConstVar.PUBLIC_CHANNEL)
            return true;
        else
            return false;
    }

    // 加入群聊 目前只有群聊和公开群组可以加入
    public ChannelMemberDto joinGroupChannel(String channelId,String uid)throws Exception{
        // 检查用户是否已经在群组中
        ChannelMemberDto res = channelMemberMapper.getInChannelMember(channelId,uid);
        if(res!=null){
            logger.info("[加入群组] 用户["+uid+"]已经在群组中");
            return res;
        }
        int channelType;
        // 检查channel类型是否为公开群组类型
        if(isPublicChannel(channelId)){
            channelType = ConstVar.PUBLIC_CHANNEL;
        }else if(isGroupChannel(channelId)){
            channelType = ConstVar.GROUP_CHANNEL;
        }else {
            throw new CommonException(StatusCode.ERROR_CHANNEL_JOIN_FAILED,"channel类型错误");
        }
        Date now = new Date();
        ChannelMemberDto channelMemberDto = new ChannelMemberDto();
        channelMemberDto.setChannelId(channelId);
        channelMemberDto.setUid(uid);
        channelMemberDto.setStatus(ConstVar.IN_CHANNEL);
        channelMemberDto.setJoinTime(now);
        channelMemberDto.setUserType(ConstVar.ATTENDER);
        channelMemberDto.setChannelType(channelType);
        channelMemberDto.setCtime(now);
        channelMemberDto.setUpdateTime(now);
        channelMemberMapper.saveChannelMember(channelMemberDto);
        // 加入到ChannelGroup中 用于接收消息
        boolean bindRes = SessionUtil.bindToChannelGroup(uid,channelId);
        if(bindRes==false){
            logger.info("用户["+uid+"]绑定群组["+channelId+"]失败");
        }
        return channelMemberDto;
    }

    // 退出群聊
    public boolean leftGroupChannel(String channelId,String uid)throws Exception{
        // 检查用户是否已经在群组中
        ChannelMemberDto res = channelMemberMapper.getInChannelMember(channelId,uid);
        if(res!=null){
            // 用户还在群组中
            // 检查是否为群聊类型
            boolean isGroupChannel = isGroupChannel(channelId);
            if(isGroupChannel) {
                // 移除channelGroup
                SessionUtil.unbindFromChannelGroup(uid,channelId);
                Date now = new Date();
                return channelMemberMapper.userLeftChannel(channelId, uid, now);
            }else {
                throw new CommonException(StatusCode.ERROR_CHANNEL_LEFT_FAILED,"channel类型错误");
            }
        }else {
            throw new CommonException(StatusCode.ERROR_CHANNEL_LEFT_FAILED,"用户不在当前channel中");
        }
    }

    // 发送创建房间消息
    public void sendChannelCreateMessage(String channelId,String fromUid,int channelType,String channelName){
        String messageId = Message.generateMessageTid();
        ChannelCreateRequestProtocal msg = new ChannelCreateRequestProtocal(channelId,fromUid,channelType,channelName,messageId);
        msg.setJsonString(JSONObject.toJSONString(msg));
        BlockingQueueProvider.publish(msg.getChannelType(),msg.getAction(),msg.getJsonString());
    }

    // 发送进入房间消息
    public void sendEnterChannelMessage(String channelId,String fromUid,int channelType){
        User user = userMapper.queryUser(fromUid);
        MemberJoinRequestProtocal msg = new MemberJoinRequestProtocal(channelId,fromUid,user.getAvatarUrl(),user.getUserName(),ConstVar.ATTENDER);
        msg.setJsonString(JSONObject.toJSONString(msg));
        BlockingQueueProvider.publish(msg.getChannelType(),msg.getAction(),msg.getJsonString());
    }

    // 发送离开房间消息
    public void sendLeftChannelMessage(String channelId,String leftUid,int channelType,int leftReason){
        MemberLeftRequestProtocal msg = new MemberLeftRequestProtocal(channelId,leftUid,leftReason);
        msg.setJsonString(JSONObject.toJSONString(msg));
        BlockingQueueProvider.publish(msg.getChannelType(),msg.getAction(),msg.getJsonString());
    }

    // 获取公开群组的channelId 没有则创建
    public String getChannelIdByPublicUrl(ChannelDto channelDto) throws Exception{
        String url = channelDto.getPublicUrl();
        String channelId = publicChannelDictMapper.getChannelIdByOuterUrl(url);
        if(channelId==null){
            channelDto.setChannelType(ConstVar.PUBLIC_CHANNEL);
            channelDto.setCreatorId("public");
            ChannelDto res = createChannel(channelDto);
            channelId = res.getChannelId();
            publicChannelDictMapper.putChannelId(channelId,url);
        }
        return channelId;
    }
}
