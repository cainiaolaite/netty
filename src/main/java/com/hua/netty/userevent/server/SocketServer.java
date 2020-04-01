package com.hua.netty.userevent.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty 创建socket 服务器
 */
public class SocketServer {
    public static void main(String[] args){
        //创建socket服务器
        ServerBootstrap socketServerBootstrap = new ServerBootstrap();
        //创建服务器NIO事件组(管连接，请求)
        NioEventLoopGroup boosNioEventLoopGroup = new NioEventLoopGroup();
        //创建客户端NIO事件组(管输入，输出)
        NioEventLoopGroup workNioEventLoopGroup = new NioEventLoopGroup();
        //socket 服务器设置事件组
        socketServerBootstrap.group(boosNioEventLoopGroup,workNioEventLoopGroup);
        //设置通道反射对象
        socketServerBootstrap.channel(NioServerSocketChannel.class);
        //设置通道处理对象
        socketServerBootstrap.childHandler(new SocketChannelInitializer());
        //设置ip 端口号
        try {
            socketServerBootstrap.bind(7070).sync();
        } catch (InterruptedException e) {
            //优雅关闭 服务器NIO事件组
            boosNioEventLoopGroup.shutdownGracefully();
            workNioEventLoopGroup.shutdownGracefully();
        }


    }
}
