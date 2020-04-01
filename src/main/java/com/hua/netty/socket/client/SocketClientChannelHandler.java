package com.hua.netty.socket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * socket 通信通道
 */
public class SocketClientChannelHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx
     * @param msg socket 客户端发过来的字符串信息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress().toString()+":"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().remoteAddress().toString()+":异常"+cause.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("my name is client");
        ctx.flush();
    }
}
