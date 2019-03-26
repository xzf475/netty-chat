package com.netty.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author Jame
 * @date 2019-03-14 15:41
 */

public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LogManager.getLogger("ServerHandler");

    private WebSocketServerHandshaker webSocketServerHandshaker;

    //private Map<String,String> userMap = new HashMap<>();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        NettyConfig.group.stream()
                .forEach(channel -> {
                    channel.writeAndFlush(msg);
                });
        logger.info(ctx.channel().id() +":"+ msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("channelActive >>>>>>>> " + ctx.channel().id());
        NettyConfig.group.add(ctx.channel());
        //发送连接成功包
        ctx.channel().writeAndFlush("connect success");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive >>>>>>>> " + ctx.channel().id());
        NettyConfig.group.remove(ctx.channel());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
