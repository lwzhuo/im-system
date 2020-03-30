package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.SingleChatProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameSingleChatHandler extends SimpleChannelInboundHandler<SingleChatProtocal> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        System.out.println("收到单聊请求:"+recMsg);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("singlechat:"+recMsg));
    }
}
