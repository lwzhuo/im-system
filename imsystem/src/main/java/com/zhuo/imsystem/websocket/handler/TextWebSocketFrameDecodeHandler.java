package com.zhuo.imsystem.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.websocket.protocal.EchoProtocal;
import com.zhuo.imsystem.websocket.protocal.Protocal;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.List;

public class TextWebSocketFrameDecodeHandler extends MessageToMessageDecoder<TextWebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out)throws Exception{
        doDecode(ctx,msg,out);
    }

    private void doDecode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out){
        // 客服端发送过来的消息
        String request = msg.text();
        JSONObject jsonObject = null;
        System.out.println("解析协议");
        try {
            msg.retain();
            jsonObject = JSONObject.parseObject(request);
            int action = jsonObject.getIntValue("action");// 获取数据类型
            out.add(JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(action)));// 获取pojo
            System.out.println("协议类型:"+action+" "+ProtocalMap.getMap().get(action).toString());
        } catch (Exception e) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("格式错误"));
            e.printStackTrace();
        }
        if (jsonObject == null) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("参数为空"));
            return;
        }
    }
}
