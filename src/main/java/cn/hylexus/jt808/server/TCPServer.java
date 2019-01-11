package cn.hylexus.jt808.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hylexus.jt808.common.TPMSConsts;
import cn.hylexus.jt808.service.codec.Decoder4LoggingOnly;
import cn.hylexus.jt808.service.handler.TCPServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;

public class TCPServer {

    private Logger log = LoggerFactory.getLogger(getClass());
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private int port;

    public TCPServer() {
    }

    public TCPServer(int port) {
        this();
        this.port = port;
    }


    public synchronized void startServer() {
        if (this.isRunning) {
            throw new IllegalStateException(this.getName() + " is already started .");
        }
        this.isRunning = true;

        Server808Thread thread = new Server808Thread(8099);
        thread.start();
    }

    public synchronized void stopServer() {
        if (!this.isRunning) {
            throw new IllegalStateException(this.getName() + " is not yet started .");
        }
        this.isRunning = false;

        try {
            Future<?> future = this.workerGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("workerGroup 无法正常停止:{}", future.cause());
            }

            future = this.bossGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                log.error("bossGroup 无法正常停止:{}", future.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.log.info("TCP服务已经停止...");
    }

    private String getName() {
        return "TCP-Server";
    }

    public static void main(String[] args) throws Exception {
        TCPServer server = new TCPServer(8099);
        server.startServer();

        // Thread.sleep(3000);
        // server.stopServer();
    }
}