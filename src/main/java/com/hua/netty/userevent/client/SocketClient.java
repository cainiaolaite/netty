package com.hua.netty.userevent.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * socket 客户端
 */
public class SocketClient {

    public static void main(String[] args){
        //新建socket 客户端
        Bootstrap bootstrap = new Bootstrap();
        //客户端设置事件组
        NioEventLoopGroup workNioEventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(workNioEventLoopGroup);
        //客户端设置通信通道
        bootstrap.channel(NioSocketChannel.class);
        try {
            //客户端设置通道
            bootstrap.handler(new SocketClientChannelInitializer());
            //客户端连接服务器
            Channel channel = bootstrap.connect("localhost",7070).sync().channel();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                try {
                    String string = br.readLine();
                    if(string != null){
                        channel.writeAndFlush(string+"\t\n");
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (InterruptedException e) {
            //优雅关闭 服务器NIO事件组
            workNioEventLoopGroup.shutdownGracefully();
        }
    }

}
