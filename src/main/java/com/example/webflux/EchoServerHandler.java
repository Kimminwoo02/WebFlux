package com.example.webflux;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;


public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String readMessage = ((ByteBuf) msg).toString (Charset.defaultCharset()); // 수신된 메시지를 문자열로 디코딩합니다.
        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열 [");
        builder.append(readMessage);
        builder.append("]");
        System.out.println(builder.toString());

        ByteBuf msgBuffer = Unpooled.buffer(); // 새로운 버퍼를 생성합니다.
        msgBuffer.writeBytes("Server Response = > received data :".getBytes()); // 버퍼에 응답 메세지를 작성합니다.

        ctx.write(msgBuffer); // 채널에 응답 메시지를 전송합니다.
        ctx.write(msg); // 수신된 메시지를 다시 그대로 전송합니다.
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush(); // 채널에 대한 작업이 완료되었음을 알리고 모든 메시지를 클라이언트로 플러시합니다.
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("오류 발생");
        cause.printStackTrace(); // 예외 내용을 출력합니다
        ctx.close(); // 예외가 발생한 채널을 닫습니다.
    }

}