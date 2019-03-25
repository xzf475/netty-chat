package com.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>
 *
 * @author Jame
 * @date 2019-03-14 17:04
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        Channel channel = bootstrap.connect("127.0.0.1", 8212).channel();
        if (channel != null){
            System.out.println("connect success");
        }
        channel.writeAndFlush(Arrays.asList("1","2","3"));
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            channel.writeAndFlush(sc.nextLine());
        }
    }
}
