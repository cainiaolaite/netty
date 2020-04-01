package com.hua.netty.socket.client;

import com.hua.netty.socket.server.SocketChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 初始化socket 通道 socket 传递的时纯数据，而不像http 是需要根据协议传递的
 */
public class SocketClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取为通道添加管道的对象
        ChannelPipeline channelPipeline = ch.pipeline();
        //添加解码器（看后面介绍）
        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        channelPipeline.addLast(new LengthFieldPrepender(4));
        //添加字符串解码管道（字节转换为字符串）用于读取数据
        channelPipeline.addLast(new StringDecoder());
        //添加字符串编码通道
        channelPipeline.addLast(new StringEncoder());
        //添加与管道互相通信的管道
        channelPipeline.addLast(new SocketClientChannelHandler());
    }
}
