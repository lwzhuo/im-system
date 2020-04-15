package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.RegisterProtocal;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;

public class TextWebSocketFrameRegisterHandler extends SimpleChannelInboundHandler<RegisterProtocal> {
    public static AttributeKey<String> USERID = AttributeKey.valueOf("uid");
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        String uidFromMsg = msg.getUid();
        String uidFromChannel = ctx.channel().attr(USERID).get();
        if(uidFromChannel.equals(uidFromMsg)){
            SessionUtil.userOnline(uidFromMsg,ctx.channel());
            System.out.println("[websocket] channel注册成功");
            ctx.writeAndFlush(new TextWebSocketFrame("注册成功"));
        }else{
            System.out.println("[websocket] channel注册失败");
            ctx.writeAndFlush(new TextWebSocketFrame("注册失败,签名验证失败:"+recMsg));
            ctx.channel().close();
        }
    }
}
