package com.cuzz.rpc.transport;

import com.cuzz.rpc.Const;
import com.cuzz.rpc.protocol.Message;
import com.cuzz.rpc.protocol.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author cuzz
 * @date 2020/12/26 14:56
 */
public class CuzzRpcServerHandler extends SimpleChannelInboundHandler<Message<Request>> {
    // 业务线程
    private static Executor executor = Executors.newCachedThreadPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Request> msg) throws Exception{
        byte extraInfo = msg.getHeader().getExtraInfo();
        if (Const.isHeartBeat(extraInfo)) { // 心跳消息直接返回
            ctx.writeAndFlush(msg);
            return;
        }
        // 非心跳消息，直接封装成Runnable提交到业务线程池中
        executor.execute(new InvokeRunnable(msg, ctx));

    }
}
