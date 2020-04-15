package com.zhuo.imsystem.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.response.ErrorResponseProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.List;

// websocket TextWebSocketFrame 解码器
public class TextWebSocketFrameDecodeHandler extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out)throws Exception{
        doDecode(ctx,msg,out);
    }

    private void doDecode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out){
        try {
            // 客服端发送过来的消息
            String request = msg.text();
            JSONObject jsonObject = null;
            msg.retain();
            jsonObject = JSONObject.parseObject(request);
            int action = jsonObject.getIntValue("action");// 获取数据类型
            out.add(JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(action)));// 获取pojo
            System.out.println("[websocket]解析协议:"+action+" "+ProtocalMap.getMap().get(action).toString());
        } catch (Exception e) {
            String res = ProtocalMap.toJSONString(new ErrorResponseProtocal("协议格式错误", StatusCode.ERROR_WEBSOCKET_PROTOCAL_FORMAT_INVALID));
            ctx.channel().writeAndFlush(new TextWebSocketFrame(res));
            e.printStackTrace();
        }
    }
}
