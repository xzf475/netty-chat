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

    private Map<String,String> userMap = new HashMap<>();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        //logger.info(msg);
        broadcastWsMsg( ctx, (String) msg );
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("channelActive>>>>>>>> " + ctx.channel().id());
        NettyConfig.group.add(ctx.channel());
        userMap.put(ctx.channel().id().asShortText(),null);
        NettyConfig.group.stream()
                .filter(channel -> channel.id() != ctx.channel().id())
                .forEach(channel -> {
                    channel.writeAndFlush(ctx.channel().id() + " come in");
                });
        logger.info(ctx.channel().id() + " come in");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive>>>>>>>> " + ctx.channel().id());
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

    private void broadcastWsMsg(ChannelHandlerContext ctx, String msg) {
        NettyConfig.group.stream()
                .filter(channel -> channel.id() != ctx.channel().id())
                .forEach(channel -> {
                    channel.writeAndFlush(ctx.channel().id() +":"+ msg);
                });
        logger.info(ctx.channel().id() +":"+ msg);
    }

}
