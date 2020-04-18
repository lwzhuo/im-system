package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.EchoRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.EchoResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class EchoHandler extends MessageHandler {
    public EchoHandler(BlockingQueueMessage message){
        this.message = message;
    }

    public void doHandler(){
        String strMsg = message.getMsg();
        EchoRequestProtocal echoRequestProtocal = (EchoRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String fromUid = echoRequestProtocal.getFromUid();
        Channel userChannel = ChannelContainer.getChannelByUserId(fromUid);
        String res = ProtocalMap.toJSONString(new EchoResponseProtocal().success(echoRequestProtocal.getMsg()));// todo  改为toString()
        userChannel.writeAndFlush(new TextWebSocketFrame(res));
    }
}
