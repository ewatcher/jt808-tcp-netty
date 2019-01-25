package com.tuocheng.jt808.vo.req;

import com.tuocheng.jt808.vo.PackageData;

/**
 * =================================
 *
 * @ClassName TerminalPropertiesReply
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-24 上午11:56
 * @Version v1.0.0
 * ==================================
 */
public class TerminalPropertiesReplyMsg extends PackageData {

    /**
     * 终端类型byte[0-1] WORD(16) <br/>
     * bit0,0:不适用客运车辆,1:适用客运车辆; <br/>
     * bit1,0:不适用危险品车辆,1:适用危险品车辆; <br/>
     * bit2,0:不适用普通货运车辆,1:适用普通货运车辆 <br/>
     * bit3,0:不适用出租车辆,1:适用出租车辆; <br/>
     * bit6,0:不支持硬盘录像,1:支持硬盘录像; <br/>
     * bit7,0:一体机,1:分体机。 <br/>
     */
    private int terminalType;

    /**
     * 制造商ID byte[2-6]   BYTE[5] 5 个字节,终端制造商编码。 <br/>
     */
    private String manufacturer;

    /**
     * 终端型号 byte[7-26] BYTE[20] <br/>
     * 20 个字节,此终端型号由制造商自行定义,位数不足时,后补“0X00”。
     */
    private String terminalModel;

    /**
     * 终端 ID byte[27-41] BYTE[7] <br/>
     * 7 个字节,由大写字母和数字组成,此终端 ID 由制造商,自行定义,位数不足时,后补“0X00”。
     */
    private String terminalID;

    /**
     * 终端 SIM 卡 ICCID byte[42-51] BCD[10] <br/>
     */
    private String SIMICCID;

    /**
     * 终端硬件版本号长度 byte[52] BYTE
     */
    private int hardwareVersionLength;

    /**
     * 终端硬件版本号 byte[53]
     */
    private String hardwareVersion;

    /**
     * 终端固件版本号长度
     */
    private int firmwareLength;

    /**
     * 终端固件版本号
     */
    private String firmwareVesion;

    public TerminalPropertiesReplyMsg() {

    }

    public TerminalPropertiesReplyMsg(PackageData packageData) {
        this();
        this.channel = packageData.getChannel();
        this.checkSum = packageData.getCheckSum();
        this.msgBodyBytes = packageData.getMsgBodyBytes();
        this.msgHeader = packageData.getMsgHeader();
    }

    public int getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTerminalModel() {
        return terminalModel;
    }

    public void setTerminalModel(String terminalModel) {
        this.terminalModel = terminalModel;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getSIMICCID() {
        return SIMICCID;
    }

    public void setSIMICCID(String SIMICCID) {
        this.SIMICCID = SIMICCID;
    }

    public int getHardwareVersionLength() {
        return hardwareVersionLength;
    }

    public void setHardwareVersionLength(int hardwareVersionLength) {
        this.hardwareVersionLength = hardwareVersionLength;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public int getFirmwareLength() {
        return firmwareLength;
    }

    public void setFirmwareLength(int firmwareLength) {
        this.firmwareLength = firmwareLength;
    }

    public String getFirmwareVesion() {
        return firmwareVesion;
    }

    public void setFirmwareVesion(String firmwareVesion) {
        this.firmwareVesion = firmwareVesion;
    }

    @Override
    public String toString() {
        return "TerminalPropertiesReplyMsg{" +
                "terminalType=" + terminalType +
                ", manufacturer='" + manufacturer + '\'' +
                ", terminalModel='" + terminalModel + '\'' +
                ", terminalID='" + terminalID + '\'' +
                ", SIMICCID='" + SIMICCID + '\'' +
                ", hardwareVersionLength=" + hardwareVersionLength +
                ", hardwareVersion='" + hardwareVersion + '\'' +
                ", firmwareLength=" + firmwareLength +
                ", firmwareVesion='" + firmwareVesion + '\'' +
                '}';
    }
}
