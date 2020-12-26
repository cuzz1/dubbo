package com.cuzz.rpc.transport;

import com.cuzz.rpc.Const;
import com.cuzz.rpc.codec.CuzzRpcDecoder;
import com.cuzz.rpc.codec.CuzzRpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.dubbo.remoting.transport.netty4.NettyEventLoopFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author cuzz
 * @date 2020/12/26 17:06
 */
public class CuzzRpcClient implements Closeable {

    protected Bootstrap clientBootstrap;
    protected EventLoopGroup group;
    private String host;
    private int port;

    public CuzzRpcClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        clientBootstrap = new Bootstrap();
        group = NettyEventLoopFactory.eventLoopGroup(
                Const.DEFAULT_IO_THREADS, "NettyClientWorker");

        clientBootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                // 指定ChannelHandler顺序
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("cuzz-rpc-encoder",
                                new CuzzRpcEncoder());
                        ch.pipeline().addLast("cuzz-rpc-decoder",
                                new CuzzRpcDecoder());
                        ch.pipeline().addLast("cuzz-handler",
                                new CuzzRpcClientHandler());
                    }
                });

    }

    public ChannelFuture connect() { // 连接指定的地址和端口
        ChannelFuture connect = clientBootstrap.connect(host, port);
        connect.awaitUninterruptibly();
        return connect;
    }


    @Override
    public void close() throws IOException {
        group.shutdownGracefully();
    }
}
