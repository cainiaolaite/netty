package com.hua.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 使用Netty 创建http 服务器
 */
public class HttpServer {
    public static void main(String[] args){
        //创建Netty服务器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //创建 服务器NIO事件组 （NIO 非阻塞）
        NioEventLoopGroup boosNioEventLoopGroup = new NioEventLoopGroup();
        //创建 客户端NIO事件组
        NioEventLoopGroup workNioEventLoopGroup = new NioEventLoopGroup();
        //放入事件组
        serverBootstrap.group(boosNioEventLoopGroup,workNioEventLoopGroup);
        //设置服务器事件处理通道反射类为NioSocket
        serverBootstrap.channel(NioServerSocketChannel.class);
        //设置服务器事件处理对象
        serverBootstrap.childHandler(new HttpChannelInitializer());
        //http服务器绑定 端口号
        try {
            serverBootstrap.bind(8080).sync();
        } catch (InterruptedException e) {
            //优雅关闭 服务器NIO事件组
            boosNioEventLoopGroup.shutdownGracefully();
            workNioEventLoopGroup.shutdownGracefully();
        }

    }
}
