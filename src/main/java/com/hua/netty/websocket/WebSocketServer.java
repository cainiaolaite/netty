package com.hua.netty.websocket;

import com.hua.netty.chat.server.ChatChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * webSocket 服务器搭建
 * WebSocket 是 HTML5 开始提供的一种在单个 TCP 连接上进行全双工通讯的协议。
 */
public class WebSocketServer {

    public static void main(String[] args){
        //创建事件组
        NioEventLoopGroup boosNioEventLoopGroup = new NioEventLoopGroup();
        NioEventLoopGroup workNioEventLoopGroup = new NioEventLoopGroup();
        //创建服务器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置事件组
        serverBootstrap.group(boosNioEventLoopGroup,workNioEventLoopGroup);
        //设置通道的反射机制
        serverBootstrap.channel(NioServerSocketChannel.class);
        //设置事件处理对象 打印日志
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        //服务器配置事件处理对象
        serverBootstrap.childHandler(new WebSocketChannelInitializer());
        //设置端口，并启动
        try {
            serverBootstrap.bind(2020).sync();
        } catch (InterruptedException e) {
            boosNioEventLoopGroup.shutdownGracefully();
            workNioEventLoopGroup.shutdownGracefully();
        }
    }
}
