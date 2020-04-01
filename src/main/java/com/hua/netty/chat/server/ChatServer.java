package com.hua.netty.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 利用netty 实现聊天服务器
 */
public class ChatServer {

    public static void main(String[] args){
        //创建服务器所需要的事件组
        NioEventLoopGroup boosNioEventLoopGroup = new NioEventLoopGroup();
        NioEventLoopGroup workNioEventLoopGroup = new NioEventLoopGroup();
        //创建服务器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //服务器配置事件组
        serverBootstrap.group(boosNioEventLoopGroup,workNioEventLoopGroup);
        //服务器配置通道反射类
        serverBootstrap.channel(NioServerSocketChannel.class);
        //服务器配置事件处理对象
        serverBootstrap.childHandler(new ChatChannelInitializer());
        //服务器配置端口号,并同步
        try {
            serverBootstrap.bind(8199).sync();
        } catch (InterruptedException e) {
            boosNioEventLoopGroup.shutdownGracefully();
            boosNioEventLoopGroup.shutdownGracefully();
        }

    }
}
