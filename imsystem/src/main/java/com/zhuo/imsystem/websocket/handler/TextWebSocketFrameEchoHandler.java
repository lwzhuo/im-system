package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.EchoProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameEchoHandler extends SimpleChannelInboundHandler<EchoProtocal> {
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EchoProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        System.out.println("收到echo:"+recMsg);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("echo:"+recMsg));
    }
}
