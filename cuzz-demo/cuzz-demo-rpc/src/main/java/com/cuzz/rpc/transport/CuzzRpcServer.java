package com.cuzz.rpc.transport;

import com.cuzz.rpc.codec.CuzzRpcDecoder;
import com.cuzz.rpc.codec.CuzzRpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.dubbo.remoting.transport.netty4.NettyEventLoopFactory;

public class CuzzRpcServer {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;

    private Channel channel;

    protected int port;

    public CuzzRpcServer(int port) throws InterruptedException {

        this.port = port;

        // 创建boss和worker两个EventLoopGroup，注意一些小细节， 

        // workerGroup 是按照中的线程数是按照 CPU 核数计算得到的，

        bossGroup = NettyEventLoopFactory.eventLoopGroup(1, "boos");
        workerGroup = NettyEventLoopFactory.eventLoopGroup(
                Math.min(Runtime.getRuntime().availableProcessors() + 1,
                        32), "worker");
        serverBootstrap = new ServerBootstrap().group(bossGroup,

                workerGroup).channel(NioServerSocketChannel.class)

                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)

                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)

                .handler(new LoggingHandler(LogLevel.INFO))

                .childHandler(new ChannelInitializer<SocketChannel>() { // 指定每个Channel上注册的ChannelHandler以及顺序

                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("cuzz-rpc-decoder",
                                new CuzzRpcDecoder());
                        ch.pipeline().addLast("cuzz-rpc-encoder",
                                new CuzzRpcEncoder());
                        ch.pipeline().addLast("server-handler",
                                new CuzzRpcServerHandler());

                    }

                });

    }

    public ChannelFuture start() throws InterruptedException {

        ChannelFuture channelFuture = serverBootstrap.bind(port);

        channel = channelFuture.channel();

        channel.closeFuture();

        return channelFuture;

    }

}
