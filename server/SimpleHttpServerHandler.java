package com.vane.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class SimpleHttpServerHandler extends ChannelInboundHandlerAdapter {
    private String result = "";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(!(msg instanceof FullHttpRequest)){
            result = "未知请求";
            send(ctx,result,HttpResponseStatus.BAD_REQUEST);
            return;
        }
        FullHttpRequest request = (FullHttpRequest)msg;
        try {
            String path = request.uri();
            String body = getBody(request);
            HttpMethod method = request.method();
            if(!"/test".equalsIgnoreCase(path)){
                result = "非法请求";
                send(ctx,result,HttpResponseStatus.BAD_REQUEST);
            }
            System.out.println("接受到" + method + "请求");
            if(HttpMethod.GET.equals(method)) {
                //业务逻辑
                System.out.println("body" + body);
                result = "GET请求";
                send(ctx,result,HttpResponseStatus.OK);
            } else if(HttpMethod.POST.equals(method)) {
                //
                System.out.println("body " + body);
                result = "POST请求";
                send(ctx,result,HttpResponseStatus.OK);
            }
        } catch (Exception e) {

        }
    }

    private String getBody(FullHttpRequest request){
        ByteBuf buf = request.content();
        return buf.toString(CharsetUtil.UTF_8);
    }

    private void send(ChannelHandlerContext ctx, String context, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,Unpooled.copiedBuffer(context,CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
