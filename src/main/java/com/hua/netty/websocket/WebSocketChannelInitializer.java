package com.hua.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        //webSocket 也是基于http 协议基础之上的协议
        //所以需要http 协议的解码器
        channelPipeline.addLast(new HttpServerCodec());
        /**
         * ChunkedWriteHandler的大致逻辑就是，首先Write方法都会把数据写入队列
         * 等到flush的时候才是把数据变成上述几种类型，然后发送到后续的handler 进行发送
         * 这个过程中如果阻塞则通过注册监听以及channelWritabilityChanged在通道可写的情况下继续去write
         */
        channelPipeline.addLast(new ChunkedWriteHandler());
        //聚合器
        channelPipeline.addLast(new HttpObjectAggregator(8192));
        //使用webSocket 协议解码器 (解析器为 /ws 路由 ws://localhost:2020/ws )
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        channelPipeline.addLast(new WebSocketSimpleChannelInboundHandler());
    }
}
