package com.hua.netty.userevent.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import static io.netty.handler.timeout.IdleState.*;

/**
 * 通道闲置状态处理
 */
public class IdleStateEventHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            switch (event.state()){
                case ALL_IDLE:
                    System.out.println("读写都闲置");
                case READER_IDLE:
                    System.out.println("读都闲置");
                case WRITER_IDLE:
                    System.out.println("写都闲置");
            }
        }
    }
}
