package com.netty.chat;

import com.netty.chat.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@SpringBootApplication
public class ChatApplication implements CommandLineRunner {

    @Resource
    NettyServer nettyServer;

    @Value("${netty.server.ip}")
    public String WS_HOST ;

    @Value("${netty.server.port}")
    public int WS_PORT ;


    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress socketAddress = new InetSocketAddress(WS_HOST,WS_PORT);
        ChannelFuture channelFuture = nettyServer.run(socketAddress);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                nettyServer.destroy();
            }
        });
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
