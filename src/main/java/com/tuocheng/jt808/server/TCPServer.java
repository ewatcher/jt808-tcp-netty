package com.tuocheng.jt808.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;

public class TCPServer {

    private Logger LOGGER = LoggerFactory.getLogger(TCPServer.class);
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
                LOGGER.error("workerGroup 无法正常停止:{}", future.cause());
            }

            future = this.bossGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                LOGGER.error("bossGroup 无法正常停止:{}", future.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.LOGGER.info("TCP服务已经停止...");
    }

    private String getName() {
        return "TCP-Server";
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer(8099);
        server.startServer();

        // Thread.sleep(3000);
        // server.stopServer();
    }
}