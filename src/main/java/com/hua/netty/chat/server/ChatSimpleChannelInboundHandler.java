package com.hua.netty.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 聊天通道读事件处理对象
 */
public class ChatSimpleChannelInboundHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 通道组
     * 存储所有连接服务器的通道
     * 通道如果出现了异常或删除，那么通道将自动从通道组里删除，不需要用户自己删除
     * 通道组的容量有限，不支持大型开发项目
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //取得当前加入的通道
        Channel thisChannel = ctx.channel();
        //循环每个连接服务器的通道，并且向他们发送消息
        channelGroup.forEach(channel -> {
            String info = thisChannel.remoteAddress().toString();
            if(thisChannel != channel){
                info = info +":"+msg+"\n";
            }else{
                info = "自己:"+msg+"\n";
            }
            channel.writeAndFlush(info);
        });
    }
    /**
     * 添加通道
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("handlerAdded 添加通道");
        //取得当前加入的通道
        Channel thisChannel = ctx.channel();
        //服务器输出上线
        System.out.println(thisChannel.remoteAddress().toString()+":上线\n");
        channelGroup.add(thisChannel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("handlerRemoved 删除通道");
        //取得当前加入的通道
        Channel thisChannel = ctx.channel();
        //服务器下线
        System.out.println(thisChannel.remoteAddress().toString()+":下线\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("channelActive 激活通道");
        //取得当前加入的通道
        Channel thisChannel = ctx.channel();
        //循环每个连接服务器的通道，并且向他们发送，我已经加入
        channelGroup.forEach(channel -> {
            if(thisChannel != channel){
                String addInfo = "【服务器通知】"+thisChannel.remoteAddress().toString()+":加入\n";
                channel.writeAndFlush(addInfo);
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("channelInactive 未激活通道");
        //取得当前加入的通道
        Channel thisChannel = ctx.channel();
        //循环每个连接服务器的通道，并且向他们发送，我已经退出
        channelGroup.forEach(channel -> {
            if(thisChannel != channel){
                String exitInfo = "【服务器通知】"+thisChannel.remoteAddress().toString()+":退出\n";
                channel.writeAndFlush(exitInfo);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //System.out.println("exceptionCaught 通道异常");
        //取得当前加入的通道
        Channel thisChannel = ctx.channel();
        //服务器下线
        System.out.println(thisChannel.remoteAddress().toString()+":断线\n");
    }

}
