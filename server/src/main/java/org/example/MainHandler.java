package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.ArrayList;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static final List<Channel> channels = new ArrayList<>();
    private String clientName;
    private static int newClientIndex = 1;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился: " + ctx);
        channels.add(ctx.channel());
        clientName = "Клиент №" + newClientIndex;
        newClientIndex++;
        broadCastMessage("SERVER"," Подключился новый клиент " + clientName);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Получено сообщение: " + msg);
        if (msg.startsWith("/")) {
            if (msg.startsWith("/changename ")) {
                String newNick = msg.split("\\s", 2)[1];
                broadCastMessage("SERVER","Клиент " + clientName + " сменил ник на " + newNick);
                clientName = newNick;
            }
            return;
        }
        broadCastMessage(clientName, msg);
    }

    public void broadCastMessage(String clientName, String message) {
        String out = String.format("[%s]: %s\n", clientName, message);
        for (Channel c : channels) {
            c.writeAndFlush(out);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Клиент: " + clientName + " отключился");
        channels.remove(ctx.channel());
        broadCastMessage("SERVER","Клиент " + clientName + " вышел из сети");
        ctx.close();
    }
}
