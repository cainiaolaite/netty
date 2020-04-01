package com.hua.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 聊天客户端
 */
public class ChatClient {

    public static void main(String[] args){
        //创建事件组
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        //设置事件组
        bootstrap.group(nioEventLoopGroup);
        //设置通道反射机制
        bootstrap.channel(NioSocketChannel.class);
        //设置通道事件处理对象
        bootstrap.handler(new ClientChannelInitializer());
        try {
            //连接服务器
            Channel channel = bootstrap.connect("localhost",8199).sync().channel();
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
            nioEventLoopGroup.shutdownGracefully();
        }
    }

}
