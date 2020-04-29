package com.zhuo.imsystem;

import com.zhuo.imsystem.queue.consumer.BlockingQueueConsumer;
import com.zhuo.imsystem.websocket.WebSocketServer;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhuo.imsystem.http.mapper")
public class ImSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImSystemApplication.class, args);
        // 启动websocket 服务器
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start();

        // 启动队列consumer
        BlockingQueueConsumer.start();

        // 群组ChannelGroup初始化
        new SessionUtil().ChannelGroupInit();
    }

}
