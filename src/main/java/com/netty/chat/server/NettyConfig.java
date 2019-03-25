package com.netty.chat.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * <p>
 *
 * @author Jame
 * @date 2019-03-14 16:32
 */
public class NettyConfig {

    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


}
