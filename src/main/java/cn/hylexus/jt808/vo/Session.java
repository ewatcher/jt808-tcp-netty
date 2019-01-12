package cn.hylexus.jt808.vo;

import java.net.SocketAddress;
import java.util.Objects;

import io.netty.channel.Channel;

public class Session {

    /**
     * session标识
     */
    private String id;

    /**
     * 终端手机号
     */
    private String terminalPhone;

    /**
     * netty通道
     */
    private Channel channel = null;

    /**
     * 鉴权标记
     */
    private boolean isAuthenticated = false;

    /**
     * 消息流水号 word(16) 按发送顺序从 0 开始循环累加
     */
    private int currentFlowId = 0;

    /**
     * 客户端上次的连接时间，该值改变的情况:
     * 1. terminal --> server 心跳包
     * 2. terminal --> server 数据包
     */
    private long lastCommunicateTimeStamp = 0L;

    public Session() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getTerminalPhone() {
        return terminalPhone;
    }

    public void setTerminalPhone(String terminalPhone) {
        this.terminalPhone = terminalPhone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String buildId(Channel channel) {
        return channel.id().asLongText();
    }

    public static Session buildSession(Channel channel) {
        return buildSession(channel, null);
    }

    public static Session buildSession(Channel channel, String phone) {
        Session session = new Session();
        session.setChannel(channel);
        session.setId(buildId(channel));
        session.setTerminalPhone(phone);
        session.setLastCommunicateTimeStamp(System.currentTimeMillis());
        return session;
    }

    public long getLastCommunicateTimeStamp() {
        return lastCommunicateTimeStamp;
    }

    public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
        this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
    }

    public SocketAddress getRemoteAddr() {
        System.out.println(this.channel.remoteAddress().getClass());

        return this.channel.remoteAddress();
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return isAuthenticated == session.isAuthenticated &&
                currentFlowId == session.currentFlowId &&
                lastCommunicateTimeStamp == session.lastCommunicateTimeStamp &&
                Objects.equals(id, session.id) &&
                Objects.equals(terminalPhone, session.terminalPhone) &&
                Objects.equals(channel, session.channel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, terminalPhone, channel, isAuthenticated, currentFlowId, lastCommunicateTimeStamp);
    }

    @Override
    public String toString() {
        return "Session [id=" + id + ", terminalPhone=" + terminalPhone + ", channel=" + channel + "]";
    }

    public synchronized int currentFlowId() {
        if (currentFlowId >= 0xffff) {
            currentFlowId = 0;
        }
        return currentFlowId++;
    }

}