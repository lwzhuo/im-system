package com.zhuo.imsystem.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.response.CommonErrorResponseProtocal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// websocket TextWebSocketFrame 解码器
public class TextWebSocketFrameDecodeHandler extends MessageToMessageDecoder<TextWebSocketFrame> {
    private static Logger logger = LoggerFactory.getLogger(TextWebSocketFrameDecodeHandler.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out)throws Exception{
        doDecode(ctx,msg,out);
    }

    private void doDecode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out){
        try {
            // 客服端发送过来的消息
            String requestMsg = msg.text();
            msg.retain();
            JSONObject jsonObject = JSONObject.parseObject(requestMsg);
            int action = jsonObject.getIntValue("action");
            jsonObject.put("jsonString",requestMsg);// 获取json格式字符串
            out.add(JSON.toJavaObject(jsonObject, ProtocalMap.getMap().get(action)));// 获取pojo
            logger.info("[websocket]解析协议:"+action+" "+ProtocalMap.getMap().get(action).toString());
        } catch (Exception e) {
            String res = ProtocalMap.toJSONString(new CommonErrorResponseProtocal("协议格式错误", StatusCode.ERROR_WEBSOCKET_PROTOCAL_FORMAT_INVALID));
            ctx.channel().writeAndFlush(new TextWebSocketFrame(res));
            e.printStackTrace();
        }
    }
}
