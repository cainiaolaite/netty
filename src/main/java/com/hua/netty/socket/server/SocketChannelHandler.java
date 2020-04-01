package com.hua.netty.socket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * socket 通信通道
 */
public class SocketChannelHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx
     * @param msg socket 客户端发过来的字符串信息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress().toString()+":"+msg);
        //像客户端写入信息
        ctx.write("my name is server");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(ctx.channel().remoteAddress().toString()+":异常"+cause.toString());
    }
}
