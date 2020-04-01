package com.zhuo.imsystem.websocket.handler;

import com.zhuo.imsystem.websocket.protocal.RegisterProtocal;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import com.zhuo.imsystem.websocket.util.Signature;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameRegisterHandler extends SimpleChannelInboundHandler<RegisterProtocal> {
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterProtocal msg) throws Exception {
        String recMsg = msg.getMsg();
        String uid = msg.getUid();
        System.out.println("[注册信令]:"+uid);
        Boolean isValid = Signature.checkRegisterSig(msg);
        if(isValid){
            SessionUtil.userOnline(uid,ctx);
            ctx.writeAndFlush(new TextWebSocketFrame("注册成功:"+recMsg));
        }else{
            ctx.writeAndFlush(new TextWebSocketFrame("注册失败,签名验证失败:"+recMsg));
            ctx.channel().close();
        }
    }
}
