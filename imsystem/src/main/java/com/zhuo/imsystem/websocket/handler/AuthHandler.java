package com.zhuo.imsystem.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.http.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static Logger logger = LoggerFactory.getLogger(AuthHandler.class);
    public static AttributeKey<String> USERID = AttributeKey.valueOf("uid");

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.getUri();
        String queryPath = uri.substring(uri.indexOf("?"));
        String param[] = queryPath.split("=");
        if(param.length!=2 && param[0]!="token"){
            logger.info("[websocket] 签名验证失败,uri 格式错误"+uri);
            ctx.close();
        }
        String token = param[1].trim();
        if(token!=null && token!=""){
            Claims claims = JWTUtil.parseJWT(token, ConstVar.JWT_SECRET);
            JSONObject jsonObject = JSONObject.parseObject(claims.getSubject());
            String uid = jsonObject.getString("uid");
            ctx.channel().attr(USERID).set(uid);
            request.setUri("/ws");
            // 传递到下一个handler：升级握手
            logger.info("[websocket] 签名验证成功");
            ctx.channel().pipeline().remove(this); // 鉴权成功 移除handler
            ctx.fireChannelRead(request.retain());
        }else {
            logger.info("[websocket] 签名验证失败,uri 没有携带token参数");
            ctx.close();
        }
    }
}