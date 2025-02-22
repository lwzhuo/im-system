package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.queue.producer.BlockingQueueProvider;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.EchoRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.EchoResponseProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// echo
public class TextWebSocketFrameEchoHandler extends SimpleChannelInboundHandler<EchoRequestProtocal> {
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EchoRequestProtocal msg) throws Exception {
//        String recMsg = msg.getMsg();
//        logger.info("[websocket]收到echo:"+recMsg);
//        String res = ProtocalMap.toJSONString(new EchoResponseProtocal());
//        ctx.writeAndFlush(new TextWebSocketFrame(res));
        BlockingQueueProvider.publish(msg.getMsgType(),msg.getAction(),msg.getJsonString());
    }
}
