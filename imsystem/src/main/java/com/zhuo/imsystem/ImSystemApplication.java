package com.zhuo.imsystem;

import com.zhuo.imsystem.websocket.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhuo.imsystem.http.mapper")
public class ImSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImSystemApplication.class, args);
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start();
    }

}
