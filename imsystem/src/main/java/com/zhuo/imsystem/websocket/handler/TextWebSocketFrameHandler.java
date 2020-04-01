package com.zhuo.imsystem.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

//处理文本协议数据，处理TextWebSocketFrame类型的数据，websocket专门处理文本的frame就是TextWebSocketFrame
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息："+msg.text());
        ctx.pipeline().addLast(new TextWebSocketFrameDecodeHandler());
        ctx.pipeline().addLast(new TextWebSocketFrameRegisterHandler());
        ctx.pipeline().addLast(new TextWebSocketFrameEchoHandler());
        ctx.pipeline().addLast(new TextWebSocketFrameSingleChatHandler());
        ctx.fireChannelRead(msg);
    }
}