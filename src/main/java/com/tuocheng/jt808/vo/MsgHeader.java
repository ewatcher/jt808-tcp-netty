package com.tuocheng.jt808.vo;

/**
 * =================================
 *
 * @ClassName MsgHeader
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-12 下午2:53
 * @Version v1.0.0
 * ==================================
 */
public class MsgHeader {
    /**
     * 消息ID
     */
    private int msgId;

    //========
    /**
     * 消息体属性
     * byte[2-3]
     */
    private int msgBodyPropsField;

    /**
     * 消息体长度
     */
    private int msgBodyLength;

    /**
     * 数据加密方式
     */
    private int encryptionType;

    /**
     * 是否分包,true==>有消息包封装项
     */
    private boolean hasSubPackage;

    /**
     * 保留位[14-15]
     */
    private String reservedBit;
    /////// ========消息体属性

    /**
     * 终端手机号
     */
    private String terminalPhone;

    /**
     * 流水号
     */
    private int flowId;

    //////// =====消息包封装项
    /**
     * 消息包封装项 byte[12-15]
     */
    private int packageInfoField;

    /**
     * 消息包总数(word(16))
     */
    private long totalSubPackage;

    /**
     * 包序号(word(16))这次发送的这个消息包是分包中的第几个消息包, 从 1 开始
     */
    private long subPackageSeq;
    //////// =====消息包封装项

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getMsgBodyLength() {
        return msgBodyLength;
    }

    public void setMsgBodyLength(int msgBodyLength) {
        this.msgBodyLength = msgBodyLength;
    }

    public int getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(int encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getTerminalPhone() {
        return terminalPhone;
    }

    public void setTerminalPhone(String terminalPhone) {
        this.terminalPhone = terminalPhone;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public boolean isHasSubPackage() {
        return hasSubPackage;
    }

    public void setHasSubPackage(boolean hasSubPackage) {
        this.hasSubPackage = hasSubPackage;
    }

    public String getReservedBit() {
        return reservedBit;
    }

    public void setReservedBit(String reservedBit) {
        this.reservedBit = reservedBit;
    }

    public long getTotalSubPackage() {
        return totalSubPackage;
    }

    public void setTotalSubPackage(long totalPackage) {
        this.totalSubPackage = totalPackage;
    }

    public long getSubPackageSeq() {
        return subPackageSeq;
    }

    public void setSubPackageSeq(long packageSeq) {
        this.subPackageSeq = packageSeq;
    }

    public int getMsgBodyPropsField() {
        return msgBodyPropsField;
    }

    public void setMsgBodyPropsField(int msgBodyPropsField) {
        this.msgBodyPropsField = msgBodyPropsField;
    }

    public void setPackageInfoField(int packageInfoField) {
        this.packageInfoField = packageInfoField;
    }

    public int getPackageInfoField() {
        return packageInfoField;
    }

    @Override
    public String toString() {
        return "MsgHeader [msgId=" + msgId + ", msgBodyPropsField=" + msgBodyPropsField + ", msgBodyLength="
                + msgBodyLength + ", encryptionType=" + encryptionType + ", hasSubPackage=" + hasSubPackage
                + ", reservedBit=" + reservedBit + ", terminalPhone=" + terminalPhone + ", flowId=" + flowId
                + ", packageInfoField=" + packageInfoField + ", totalSubPackage=" + totalSubPackage
                + ", subPackageSeq=" + subPackageSeq + "]";
    }
}
