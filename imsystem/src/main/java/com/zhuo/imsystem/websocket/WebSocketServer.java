package com.zhuo.imsystem.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import com.zhuo.imsystem.websocket.handler.WebSocketChildChannelHandler;
import javax.annotation.Resource;
import java.util.Date;

public class WebSocketServer {
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workersGroup = new NioEventLoopGroup();
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();
    private final WebSocketChildChannelHandler WebSocketChildChannelHandler = new WebSocketChildChannelHandler();
    private static final int PORT = 8000;

    public void start(){
        System.out.println("开始启动websocket服务");
        serverBootstrap.group(bossGroup,workersGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
                .option(ChannelOption.TCP_NODELAY, true) //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                .childHandler(WebSocketChildChannelHandler); //绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
