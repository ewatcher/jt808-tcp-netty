package com.tuocheng.jt808.vo;

import java.util.Arrays;

import com.alibaba.fastjson.annotation.JSONField;

import io.netty.channel.Channel;

public class PackageData {

    /**
     * 16byte 消息头
     */
    protected MsgHeader msgHeader;

    /**
     * 消息体字节数组
     */
    @JSONField(serialize = false)
    protected byte[] msgBodyBytes;

    /**
     * 校验码 1byte
     */
    protected int checkSum;

    @JSONField(serialize = false)
    protected Channel channel;

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public byte[] getMsgBodyBytes() {
        return msgBodyBytes;
    }

    public void setMsgBodyBytes(byte[] msgBodyBytes) {
        this.msgBodyBytes = msgBodyBytes;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "PackageData [msgHeader=" + msgHeader + ", msgBodyBytes=" + Arrays.toString(msgBodyBytes) + ", checkSum="
                + checkSum + ", address=" + channel + "]";
    }


}
