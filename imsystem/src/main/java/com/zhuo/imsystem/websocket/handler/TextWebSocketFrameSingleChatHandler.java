package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.SingleChatProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TextWebSocketFrameSingleChatHandler extends SimpleChannelInboundHandler<SingleChatProtocal> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        System.out.println("收到单聊请求:"+recMsg);
        ctx.writeAndFlush("singlechat"+recMsg);
    }
}
