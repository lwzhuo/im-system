package com.zhuo.imsystem.websocket.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("http-codec", new HttpServerCodec()); // HTTP编码解码器
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536)); // 把HTTP头、HTTP体拼成完整的HTTP请求
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler()); // 方便大文件传输，不过实质上都是短的文本数据
        //ws://server:port/context_path
        //ws://localhost:9999/ws
        //参数指的是contex_path
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
        //websocket定义了传递数据的6中frame类型
        ch.pipeline().addLast(new TextWebSocketFrameHandler());
    }
}
