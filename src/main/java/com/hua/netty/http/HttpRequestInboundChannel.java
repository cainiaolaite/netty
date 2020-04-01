package com.hua.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 *  http 请求进入时获取信息的通道
 */
public class HttpRequestInboundChannel extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 通道读取 http 请求信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        /**
         * 如果 请求对象的类型为 http 类型
         */
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)msg;
            String url = httpRequest.uri();
            System.out.println("收到http请求，url:"+url);
            //创建响应的字节缓冲区
            ByteBuf responseByteBuf = Unpooled.copiedBuffer("helloWorld", CharsetUtil.UTF_8);
            //创建完整的http响应对象
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,HttpResponseStatus.OK,responseByteBuf);
            //设置响应头 内容类型
            fullHttpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            //设置响应头 内容长度
            fullHttpResponse.headers().add(HttpHeaderNames.CONTENT_LENGTH,responseByteBuf.readableBytes());
            //将数据写入缓冲区
            ctx.write(fullHttpResponse);
            //刷新缓冲区后数据就能返回到客户端
            ctx.flush();
            //也可以写成
            //TODO ctx.writeAndFlush(fullHttpResponse);
        }

    }
}
