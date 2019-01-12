package com.tuocheng.jt808.server;

import com.tuocheng.jt808.common.TPMSConsts;
import com.tuocheng.jt808.service.handler.TCPServerHandler;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * =================================
 *
 * @ClassName Server808Thread
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-11 上午12:58
 * @Version v1.0.0
 * ==================================
 */
public class Server808Thread extends Thread {

    private Logger LOGGER = LoggerFactory.getLogger(Server808Thread.class);
    private volatile boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private int port = 8099;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public Server808Thread(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public Server808Thread(int port) {
        this.port = port;
    }


    public Server808Thread(ThreadGroup group, Runnable target, String name, long stackSize, EventLoopGroup bossGroup) {
        super(group, target, name, stackSize);
        this.bossGroup = bossGroup;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        try {
            this.bind();
        } catch (Exception e) {
            LOGGER.info("TCP服务启动出错:{}", e.getMessage());
            e.printStackTrace();
        }
        super.run();
    }

    private void bind() throws Exception {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast("idleStateHandler",
                                new IdleStateHandler(TPMSConsts.tcp_client_idle_minutes, 0, 0, TimeUnit.MINUTES));
                        // 1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
                        ch.pipeline().addLast(
                                new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[]{0x7e}),
                                        Unpooled.copiedBuffer(new byte[]{0x7e, 0x7e})));
                        // ch.pipeline().addLast(new PackageDataDecoder());
                        ch.pipeline().addLast(new TCPServerHandler());
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        LOGGER.info("TCP服务启动完毕,port={}", this.port);
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        channelFuture.channel().closeFuture().sync();
    }
}
