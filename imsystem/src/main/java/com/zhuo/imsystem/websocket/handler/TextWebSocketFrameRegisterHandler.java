package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.RegisterRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.CommonErrorResponseProtocal;
import com.zhuo.imsystem.websocket.protocal.response.RegisterResponseProtocal;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;

// 用户注册到websocket服务
public class TextWebSocketFrameRegisterHandler extends SimpleChannelInboundHandler<RegisterRequestProtocal> {
    public static AttributeKey<String> USERID = AttributeKey.valueOf("uid");
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        String uidFromMsg = msg.getFromUid();
        String uidFromChannel = ctx.channel().attr(USERID).get();
        if(uidFromChannel.equals(uidFromMsg)){
            SessionUtil.userOnline(uidFromMsg,ctx.channel());// 将当前用户的channel注册到channel容器中
            System.out.println("[websocket] channel注册成功");
            String res = ProtocalMap.toJSONString(new RegisterResponseProtocal().success("注册成功"));
            ctx.writeAndFlush(new TextWebSocketFrame(res));
        }else{
            System.out.println("[websocket] channel注册失败");
            String res = ProtocalMap.toJSONString(new CommonErrorResponseProtocal("注册失败", StatusCode.ERROR_WEBSOCKET_CHANNEL_REGISTER_FAILED));
            ctx.writeAndFlush(new TextWebSocketFrame(res));
            ctx.channel().close();
        }
    }
}
