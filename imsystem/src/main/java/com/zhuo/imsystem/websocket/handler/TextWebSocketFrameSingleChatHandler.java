package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.SingleChatProtocal;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameSingleChatHandler extends SimpleChannelInboundHandler<SingleChatProtocal> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        String toUid = msg.getTo_uid();
        System.out.println("收到单聊请求:"+recMsg+" to uid:"+toUid);
        ChannelHandlerContext userContext = SessionUtil.getUserContext(toUid);
        if(userContext==null){
            ctx.writeAndFlush(new TextWebSocketFrame("用户不在线"));
        }else {
            userContext.writeAndFlush(new TextWebSocketFrame(recMsg));
        }
    }
}
