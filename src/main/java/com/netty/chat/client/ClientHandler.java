package com.netty.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 *
 * @author Jame
 * @date 2019-03-14 17:31
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LogManager.getLogger("ClientHandler");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        //打印服务端的发送数据
        logger.info(ctx.channel().id() +":"+ s);
    }
}
