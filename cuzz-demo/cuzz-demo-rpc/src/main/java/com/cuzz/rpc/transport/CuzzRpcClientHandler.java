package com.cuzz.rpc.transport;

import com.cuzz.rpc.Const;
import com.cuzz.rpc.protocol.Message;
import com.cuzz.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class CuzzRpcClientHandler extends SimpleChannelInboundHandler<Message<Response>> {
    protected void channelRead0(ChannelHandlerContext ctx, Message<Response> message) throws Exception {
        NettyResponseFuture responseFuture =
                Connection.IN_FLIGHT_REQUEST_MAP
                        .remove(message.getHeader().getMessageId());
        Response response = message.getContent();
        // 心跳消息特殊处理
        if (response == null && Const.isHeartBeat(
                message.getHeader().getExtraInfo())) {
            response = new Response();
            response.setCode(Const.HEARTBEAT_CODE);
        }
        responseFuture.getPromise().setSuccess(response.getResult());
    }
}