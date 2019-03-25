package com.netty.chat.server;

import io.netty.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * <p>
 *
 * @author Jame
 * @date 2019-03-14 15:28
 */
@Component
public class NettyServer {

    private static Logger logger = LogManager.getLogger("NettyServer");

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;

    public ChannelFuture run(InetSocketAddress inetSocketAddress){
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE);
            channelFuture = bootstrap.bind(inetSocketAddress).syncUninterruptibly();
            channel = channelFuture.channel();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (channelFuture!=null && channelFuture.isSuccess()){
                logger.info("NettyServer run success");
            }else {
                logger.info("NettyServer run fail");
            }
        }
        return channelFuture;
    }

    public void destroy() {
        logger.info("NettyServer destroy");
        if(channel != null) { channel.close();}
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        NettyConfig.group.close();
        logger.info("NettyServer destroy success");
    }
}
