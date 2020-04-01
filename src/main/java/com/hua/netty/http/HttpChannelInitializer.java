package com.hua.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * http 通道初始化
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 初始化通道
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("SocketChannel 初始化通道,为管道添加通道");
        //为通道添加管道 -- 每个通道都有自己的管道，在创建新通道时会自动创建。
        ChannelPipeline channelPipeline = ch.pipeline();
        //添加httpServerCodec 管道  httpServerCodec 集成了 编码和解码
        channelPipeline.addLast("httpServerCodec",new HttpServerCodec());
        //添加自定义http请求进入的管道
        channelPipeline.addLast("httpRequestInbound",new HttpRequestInboundChannel());
        //观察通道的生命周期
        channelPipeline.addLast("observeChannelLifeCycle",new ObserveChannelLifeCycle());
    }
}
