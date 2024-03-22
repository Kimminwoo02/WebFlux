package com.example.webflux;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class EchoServer{
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 쓰레드 수가 최대 1개인 쓰레드 풀을 생성하여 bossGroup에 할당합니다.
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 쓰레드 풀을 생성하여 workerGroup에 할당합니다.
        try{
            ServerBootstrap b = new ServerBootstrap();  // 서버 부트스트랩 객체를 생성하여 변수 b에 할당합니다.
            b.group(bossGroup, workerGroup) // bossGroup 과 workerGroup을 사용하여 부트스트랩 그룹을 설정합니다.
                    .channel(NioServerSocketChannel.class) // NioServerSocketChannel 클래스를 사용하여 채널을 설정합니다.
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline(); // 소켓 채널의 파이프라인을 가져와 p에 할당합니다.
                            p.addLast(new EchoServerHandler()); // EchoServerHandler를 파이프 라인에 추가합니다.

                        }
            });
            ChannelFuture f = b.bind(8888).sync(); // 지정된 포트(8888)로 서버를 바인딩하고 바인딩 작업이 완료될 때까지 대기한 후 ChannelFuture 객체 f에 할당합니다.
            System.out.println("서버 시작");
            f.channel().closeFuture().sync(); // 채널의 closeFuture를 얻어서 닫힐 때까지 대기합니다
        } finally{
            workerGroup.shutdownGracefully(); // workerGroup에 대해 graceful shutdown을 수행합니다.
            bossGroup.shutdownGracefully(); // bossGroup에 대해 graceful shutdown을 수행합니다.
        }
    }
}