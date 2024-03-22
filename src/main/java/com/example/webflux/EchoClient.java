package com.example.webflux;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient{
    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup(); // 이벤트 루프 그룹을 생성합니다.

        try{
            Bootstrap b = new Bootstrap(); // 클라이언트 부트스트랩 객체를 생성합니다.
            b.group(group) // 이벤트 루프 그룹을 설정합니다.
                    .channel(NioSocketChannel.class) // 클라이언트 소켓 채널의 유형을 설정합니다.
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline(); // 소켓 채널의 파이프라인을 가져와서 p에 할당합니다.
                            p.addLast(new EchoClientHandler()); // EchoClientHandler를 파이프라인에 추가합니다.
                        }
            });
            ChannelFuture f = b.connect("localhost",8888).sync();  // localhost의 8888 포트에 연결하고 연결이 완료될 때 까지 대기합니다.
            f.channel().closeFuture().sync(); // 연결된 채널의 closeFuture를 대기하여 연결 종료될 때 가지 대기합니다.
        }finally{
            group.shutdownGracefully(); //이벤트 루프 그룹 종료합니다.
        }

    }

}