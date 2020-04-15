package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.EchoRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.EchoResponseProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

// echo
public class TextWebSocketFrameEchoHandler extends SimpleChannelInboundHandler<EchoRequestProtocal> {
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EchoRequestProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        System.out.println("[websocket]收到echo:"+recMsg);
        String res = ProtocalMap.toJSONString(new EchoResponseProtocal());
        ctx.writeAndFlush(new TextWebSocketFrame(res));
    }
}
