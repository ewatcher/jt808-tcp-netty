package com.tuocheng.jt808.vo.req;


import com.tuocheng.jt808.vo.PackageData;

import java.util.Date;

/**
 * 位置信息汇报消息
 *
 * @author hylexus
 */
public class LocationInfoUploadMsg extends PackageData {
    /**
     * 报警标志byte[0-3](DWORD(32))
     */
    private int warningFlagField;

    /**
     * byte[4-7] 状态(DWORD(32))
     */
    private int statusField;

    /**
     * byte[8-11] 纬度(DWORD(32))以度为单位的纬度值乘以 10 的 6 次方,精确到百万
     * 分之一度
     */
    private double latitude;

    /**
     * byte[12-15] 经度(DWORD(32))以度为单位的纬度值乘以 10 的 6 次方,精确到百万
     * 分之一度
     */
    private double longitude;

    /**
     * byte[16-17] 高程(WORD(16)) 海拔高度，单位为米（ m）
     */
    private int elevation;

    /**
     * byte[18-19] 速度(WORD) 1/10km/h
     */
    private double speed;

    /**
     * byte[20-21] 方向(WORD) 0-359，正北为 0，顺时针
     */
    private int direction;

    /**
     * byte[22-x] 时间(BCD[6]) YY-MM-DD-hh-mm-ss
     * GMT+8 时间，本标准中之后涉及的时间均采用此时区
     */
    private String gpsTime;


    /**
     * 位置上传时间 格式YYYY-MM-dd-HH-mm-ss add
     */
    private String uploadTime;

    //================附加消息===============
    /**
     * 0x01 byte[28-x] 里程,DWORD,1/10km,对应车上里程表读数
     */
    private double carMileage;

    /**
     * 0x02 油量,WORD,1/10L,对应车上油量表读数
     */
    private double oilMass;

    /**
     * 0x03 行驶记录功能获取的速度,WORD,1/10km/h
     */
    private double driverRecordSpeed;

    /**
     * 0x04 需要人工确认报警事件的 ID,WORD,从 1 开始计数
     */
    private int manulConfirmEventId;

    /**
     * 0x11 超速报警附加信息
     */
    private int overSpeedAddMsg;

    /**
     * overSpeedAddMsg的信息构成元素：位置类型
     * 0:无特定位置;1:圆形区域;2:矩形区域;3:多边形区域;4:路段
     */
    private int overSpeedAddMsgLocateType;
    /**
     * overSpeedAddMsg的信息构成元素：区域或路段 ID
     * 若位置类型为 0,无该字段
     */
    private int overSpeedAddMsgRoadId;


    /**
     * 0x12 6 进出区域/路线报警附加信息:位置类型
     * 1:圆形区域;2:矩形区域;3:多边形区域;4:路段
     */
    private int lineAlarmAddMsgType;
    /**
     * 0x12 6 进出区域/路线报警附加信息:区域或线路 ID
     */
    private int lineAlarmAddMsgRoadId;
    /**
     * 0x12 6 进出区域/路线报警附加信息:BYTE(0:进;1:出)
     */
    private int lineAlarmAddMsgDirection;

    /**
     * 0x13 7 路段行驶时间不足/过长报警附加信息:路段 ID(DWORD)
     */
    private int lineDriverTimeAddMsgRoadId;
    /**
     * 0x13 7 路段行驶时间不足/过长报警附加信息:路段行驶时间(WORD)单位为秒(s)
     */
    private String lineDriverTimeAddMsgDriverTime;
    /**
     * 0x13 7 路段行驶时间不足/过长报警附加信息:结果(BYTE)(0:不足;1:过长)
     */
    private int lineDriverTimeAddMsgResult;

    /**
     * 0x25 4 扩展车辆信号状态位
     */
    private String extendCardSignal;

    /**
     * 0x2A IO状态位
     */
    private String ioStateBit;

    /**
     * 0x2B 4 模拟量,bit0-15,AD0;bit16-31,AD1。
     */
    private String analogQuanlity;

    /**
     * 0x30 1 BYTE 无线通信网络信号强度
     */
    private String wirelessSignalStrength;

    /**
     * 0x31 1 BYTE GNSS 定位卫星数
     */
    private String GNSSLocateStartTotal;

    /**
     * 0xE0 后续自定义信息长度
     */
    private int customLength;

    //========================报警标志
    /**
     * 0 紧急报警,触动报警开关后触发
     */
    private int emergentAlarmWarning;

    /**
     * 1 超速报警
     */
    private int overSpeedDriveWarning;

    /**
     * 2 疲劳驾驶
     */
    private int tiredDriveWarning;

    /**
     * 3 危险预警
     */
    private int dangerEarlyWarming;

    /**
     * 4 GNSS 模块发生故障
     */
    private int gnssFaultWarning;

    /**
     * 5 GNSS 天线未接或被剪断
     */
    private int gnssWireCutWarning;

    /**
     * 6 GNSS 天线短路
     */
    private int gnssWireSnappedWarning;

    /**
     * 7 终端主电源欠压
     */
    private int terminalMainPowerLowWarning;

    /**
     * 8  终端主电源掉电
     */
    private int terminalMainPowerLostWarning;

    /**
     * 9 终端 LCD 或显示器故障
     */
    private int lcdFaultWarning;

    /**
     * 10 TTS 模块故障
     */
    private int tssFaultWarning;

    /**
     * 11 摄像头故障
     */
    private int cameraFaultWarning;

    /**
     * 12 道路运输证 IC 卡模块故障
     */
    private int icCardFaultWarning;

    /**
     * 13 超速预警
     */
    private int overSpeedEarlyWarming;

    /**
     * 14 疲劳驾驶预警
     */
    private int tiredEarlyWarming;

    /**
     * 18 当天累计驾驶超时
     */
    private int overDriverTimeCountWarning;

    /**
     * 19 超时停车
     */
    private int stopOvertimeWarning;

    /**
     * 20 1:进出区域
     */
    private int inOutTownWarning;

    /**
     * 21 1:进出路线
     */
    private int inOutLineWarning;

    /**
     * 22 1:路段行驶时间不足/过长
     */
    private int lineDriverTimeWarning;

    /**
     * 23 路线偏离报警
     */
    private int lineDeviateWarning;

    /**
     * 24 车辆 VSS 故障
     */
    private int vssFaultWarning;

    /**
     * 25 车辆油量异常
     */
    private int oilUnusualWarning;

    /**
     * 26 车辆被盗(通过车辆防盗器)
     */
    private int beStolenWarning;

    /**
     * 27 车辆非法点火
     */
    private int illegalStartWarning;

    /**
     * 28 车辆非法位移
     */
    private int illegalShiftWarning;

    /**
     * 29 碰撞预警
     */
    private int impactEarlyWarming;

    /**
     * 30 侧翻预警
     */
    private int oneSideEarlyWarming;

    /**
     * 31 非法开门报警(终端未设置区域时,不判断非法开门)
     */
    private int illegalOpenDoorWarning;

    //===========状态标志

    /**
     * 0 0:ACC 关;1: ACC 开
     */
    private int accStatus;

    /**
     * 1 0:未定位;1:定位
     */
    private int locateStatus;

    /**
     *2 0:北纬;1:南纬
     */
    private int latitudeStatus;

    /**
     *3 0:东经;1:西经
     */
    private int longitudeStatus;

    /**
     *4 0:运营状态;1:停运状态
     */
    private int besinessStatus;

    /**
     *5 0:经纬度未经保密插件加密;1:经纬度已经保密插件加密
     */
    private int lanAndLongAddPluginStatus;

    /**
     *6-7 保留
     */

    /**
     *8-9 00:空车;01:半载;10:保留;11:满载
     */
    private int loadPassengerStatus;

    /**
     *10  0:车辆油路正常;1:车辆油路断开
     */
    private int oilRoadStatus;

    /**
     *11 0:车辆电路正常;1:车辆电路断开
     */
    private int electricRoadStatus;

    /**
     *12 0:车门解锁;1:车门加锁
     */
    private int carLookStatus;

    /**
     *13 0:门 1 关;1:门 1 开(前门)
     */
    private int door1Status;

    /**
     *14 0:门 2 关;1:门 2 开(中门)
     */
    private int door2Status;

    /**
     *15 0:门 3 关;1:门 3 开(后门)
     */
    private int door3Status;

    /**
     *16 0:门 4 关;1:门 4 开(驾驶席门)
     */
    private int door4Status;

    /**
     *17 0:门 5 关;1:门 5 开(自定义)
     */
    private int door5Status;

    /**
     *18 0:未使用 GPS 卫星进行定位;1:使用 GPS 卫星进行定位
     */
    private int gPSLocateStatus;

    /**
     *19 0:未使用北斗卫星进行定位;1:使用北斗卫星进行定位
     */
    private int beidouLocateStatus;

    /**
     *20 0:未使用 GLONASS 卫星进行定位;1:使用 GLONASS 卫星进行定位
     */
    private int gLONASSLocateStatus;

    /**
     *21 0:未使用 Galileo 卫星进行定位;1:使用 Galileo 卫星进行定位
     */
    private int GalileoLocateStatus;

    /**
     *22-23 保留
     */

    public LocationInfoUploadMsg() {
    }

    public LocationInfoUploadMsg(PackageData packageData) {
        this();
        this.channel = packageData.getChannel();
        this.checkSum = packageData.getCheckSum();
        this.msgBodyBytes = packageData.getMsgBodyBytes();
        this.msgHeader = packageData.getMsgHeader();
    }

    public int getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(int accStatus) {
        this.accStatus = accStatus;
    }

    public int getLocateStatus() {
        return locateStatus;
    }

    public void setLocateStatus(int locateStatus) {
        this.locateStatus = locateStatus;
    }

    public int getLatitudeStatus() {
        return latitudeStatus;
    }

    public void setLatitudeStatus(int latitudeStatus) {
        this.latitudeStatus = latitudeStatus;
    }

    public int getLongitudeStatus() {
        return longitudeStatus;
    }

    public void setLongitudeStatus(int longitudeStatus) {
        this.longitudeStatus = longitudeStatus;
    }

    public int getBesinessStatus() {
        return besinessStatus;
    }

    public void setBesinessStatus(int besinessStatus) {
        this.besinessStatus = besinessStatus;
    }

    public int getLanAndLongAddPluginStatus() {
        return lanAndLongAddPluginStatus;
    }

    public void setLanAndLongAddPluginStatus(int lanAndLongAddPluginStatus) {
        this.lanAndLongAddPluginStatus = lanAndLongAddPluginStatus;
    }

    public int getLoadPassengerStatus() {
        return loadPassengerStatus;
    }

    public void setLoadPassengerStatus(int loadPassengerStatus) {
        this.loadPassengerStatus = loadPassengerStatus;
    }

    public int getOilRoadStatus() {
        return oilRoadStatus;
    }

    public void setOilRoadStatus(int oilRoadStatus) {
        this.oilRoadStatus = oilRoadStatus;
    }

    public int getElectricRoadStatus() {
        return electricRoadStatus;
    }

    public void setElectricRoadStatus(int electricRoadStatus) {
        this.electricRoadStatus = electricRoadStatus;
    }

    public int getCarLookStatus() {
        return carLookStatus;
    }

    public void setCarLookStatus(int carLookStatus) {
        this.carLookStatus = carLookStatus;
    }

    public int getDoor1Status() {
        return door1Status;
    }

    public void setDoor1Status(int door1Status) {
        this.door1Status = door1Status;
    }

    public int getDoor2Status() {
        return door2Status;
    }

    public void setDoor2Status(int door2Status) {
        this.door2Status = door2Status;
    }

    public int getDoor3Status() {
        return door3Status;
    }

    public void setDoor3Status(int door3Status) {
        this.door3Status = door3Status;
    }

    public int getDoor4Status() {
        return door4Status;
    }

    public void setDoor4Status(int door4Status) {
        this.door4Status = door4Status;
    }

    public int getDoor5Status() {
        return door5Status;
    }

    public void setDoor5Status(int door5Status) {
        this.door5Status = door5Status;
    }

    public int getgPSLocateStatus() {
        return gPSLocateStatus;
    }

    public void setgPSLocateStatus(int gPSLocateStatus) {
        this.gPSLocateStatus = gPSLocateStatus;
    }

    public int getBeidouLocateStatus() {
        return beidouLocateStatus;
    }

    public void setBeidouLocateStatus(int beidouLocateStatus) {
        this.beidouLocateStatus = beidouLocateStatus;
    }

    public int getgLONASSLocateStatus() {
        return gLONASSLocateStatus;
    }

    public void setgLONASSLocateStatus(int gLONASSLocateStatus) {
        this.gLONASSLocateStatus = gLONASSLocateStatus;
    }

    public int getGalileoLocateStatus() {
        return GalileoLocateStatus;
    }

    public void setGalileoLocateStatus(int galileoLocateStatus) {
        GalileoLocateStatus = galileoLocateStatus;
    }

    public int getEmergentAlarmWarning() {
        return emergentAlarmWarning;
    }

    public void setEmergentAlarmWarning(int emergentAlarmWarning) {
        this.emergentAlarmWarning = emergentAlarmWarning;
    }

    public int getOverSpeedDriveWarning() {
        return overSpeedDriveWarning;
    }

    public void setOverSpeedDriveWarning(int overSpeedDriveWarning) {
        this.overSpeedDriveWarning = overSpeedDriveWarning;
    }

    public int getTiredDriveWarning() {
        return tiredDriveWarning;
    }

    public void setTiredDriveWarning(int tiredDriveWarning) {
        this.tiredDriveWarning = tiredDriveWarning;
    }

    public int getDangerEarlyWarming() {
        return dangerEarlyWarming;
    }

    public void setDangerEarlyWarming(int dangerEarlyWarming) {
        this.dangerEarlyWarming = dangerEarlyWarming;
    }

    public int getGnssFaultWarning() {
        return gnssFaultWarning;
    }

    public void setGnssFaultWarning(int gnssFaultWarning) {
        this.gnssFaultWarning = gnssFaultWarning;
    }

    public int getGnssWireCutWarning() {
        return gnssWireCutWarning;
    }

    public void setGnssWireCutWarning(int gnssWireCutWarning) {
        this.gnssWireCutWarning = gnssWireCutWarning;
    }

    public int getGnssWireSnappedWarning() {
        return gnssWireSnappedWarning;
    }

    public void setGnssWireSnappedWarning(int gnssWireSnappedWarning) {
        this.gnssWireSnappedWarning = gnssWireSnappedWarning;
    }

    public int getTerminalMainPowerLowWarning() {
        return terminalMainPowerLowWarning;
    }

    public void setTerminalMainPowerLowWarning(int terminalMainPowerLowWarning) {
        this.terminalMainPowerLowWarning = terminalMainPowerLowWarning;
    }

    public int getTerminalMainPowerLostWarning() {
        return terminalMainPowerLostWarning;
    }

    public void setTerminalMainPowerLostWarning(int terminalMainPowerLostWarning) {
        this.terminalMainPowerLostWarning = terminalMainPowerLostWarning;
    }

    public int getLcdFaultWarning() {
        return lcdFaultWarning;
    }

    public void setLcdFaultWarning(int lcdFaultWarning) {
        this.lcdFaultWarning = lcdFaultWarning;
    }

    public int getTssFaultWarning() {
        return tssFaultWarning;
    }

    public void setTssFaultWarning(int tssFaultWarning) {
        this.tssFaultWarning = tssFaultWarning;
    }

    public int getCameraFaultWarning() {
        return cameraFaultWarning;
    }

    public void setCameraFaultWarning(int cameraFaultWarning) {
        this.cameraFaultWarning = cameraFaultWarning;
    }

    public int getIcCardFaultWarning() {
        return icCardFaultWarning;
    }

    public void setIcCardFaultWarning(int icCardFaultWarning) {
        this.icCardFaultWarning = icCardFaultWarning;
    }

    public int getOverSpeedEarlyWarming() {
        return overSpeedEarlyWarming;
    }

    public void setOverSpeedEarlyWarming(int overSpeedEarlyWarming) {
        this.overSpeedEarlyWarming = overSpeedEarlyWarming;
    }

    public int getTiredEarlyWarming() {
        return tiredEarlyWarming;
    }

    public void setTiredEarlyWarming(int tiredEarlyWarming) {
        this.tiredEarlyWarming = tiredEarlyWarming;
    }

    public int getOverDriverTimeCountWarning() {
        return overDriverTimeCountWarning;
    }

    public void setOverDriverTimeCountWarning(int overDriverTimeCountWarning) {
        this.overDriverTimeCountWarning = overDriverTimeCountWarning;
    }

    public int getStopOvertimeWarning() {
        return stopOvertimeWarning;
    }

    public void setStopOvertimeWarning(int stopOvertimeWarning) {
        this.stopOvertimeWarning = stopOvertimeWarning;
    }

    public int getInOutTownWarning() {
        return inOutTownWarning;
    }

    public void setInOutTownWarning(int inOutTownWarning) {
        this.inOutTownWarning = inOutTownWarning;
    }

    public int getInOutLineWarning() {
        return inOutLineWarning;
    }

    public void setInOutLineWarning(int inOutLineWarning) {
        this.inOutLineWarning = inOutLineWarning;
    }

    public int getLineDriverTimeWarning() {
        return lineDriverTimeWarning;
    }

    public void setLineDriverTimeWarning(int lineDriverTimeWarning) {
        this.lineDriverTimeWarning = lineDriverTimeWarning;
    }

    public int getLineDeviateWarning() {
        return lineDeviateWarning;
    }

    public void setLineDeviateWarning(int lineDeviateWarning) {
        this.lineDeviateWarning = lineDeviateWarning;
    }

    public int getVssFaultWarning() {
        return vssFaultWarning;
    }

    public void setVssFaultWarning(int vssFaultWarning) {
        this.vssFaultWarning = vssFaultWarning;
    }

    public int getOilUnusualWarning() {
        return oilUnusualWarning;
    }

    public void setOilUnusualWarning(int oilUnusualWarning) {
        this.oilUnusualWarning = oilUnusualWarning;
    }

    public int getBeStolenWarning() {
        return beStolenWarning;
    }

    public void setBeStolenWarning(int beStolenWarning) {
        this.beStolenWarning = beStolenWarning;
    }

    public int getIllegalStartWarning() {
        return illegalStartWarning;
    }

    public void setIllegalStartWarning(int illegalStartWarning) {
        this.illegalStartWarning = illegalStartWarning;
    }

    public int getIllegalShiftWarning() {
        return illegalShiftWarning;
    }

    public void setIllegalShiftWarning(int illegalShiftWarning) {
        this.illegalShiftWarning = illegalShiftWarning;
    }

    public int getImpactEarlyWarming() {
        return impactEarlyWarming;
    }

    public void setImpactEarlyWarming(int impactEarlyWarming) {
        this.impactEarlyWarming = impactEarlyWarming;
    }

    public int getOneSideEarlyWarming() {
        return oneSideEarlyWarming;
    }

    public void setOneSideEarlyWarming(int oneSideEarlyWarming) {
        this.oneSideEarlyWarming = oneSideEarlyWarming;
    }

    public int getIllegalOpenDoorWarning() {
        return illegalOpenDoorWarning;
    }

    public void setIllegalOpenDoorWarning(int illegalOpenDoorWarning) {
        this.illegalOpenDoorWarning = illegalOpenDoorWarning;
    }

    public int getWarningFlagField() {
        return warningFlagField;
    }

    public void setWarningFlagField(int warningFlagField) {
        this.warningFlagField = warningFlagField;
    }

    public int getStatusField() {
        return statusField;
    }

    public void setStatusField(int statusField) {
        this.statusField = statusField;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    //===========附加信息属性


    public double getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(double carMileage) {
        this.carMileage = carMileage;
    }

    public double getOilMass() {
        return oilMass;
    }

    public void setOilMass(double oilMass) {
        this.oilMass = oilMass;
    }

    public double getDriverRecordSpeed() {
        return driverRecordSpeed;
    }

    public void setDriverRecordSpeed(double driverRecordSpeed) {
        this.driverRecordSpeed = driverRecordSpeed;
    }

    public int getManulConfirmEventId() {
        return manulConfirmEventId;
    }

    public void setManulConfirmEventId(int manulConfirmEventId) {
        this.manulConfirmEventId = manulConfirmEventId;
    }

    public int getOverSpeedAddMsg() {
        return overSpeedAddMsg;
    }

    public void setOverSpeedAddMsg(int overSpeedAddMsg) {
        this.overSpeedAddMsg = overSpeedAddMsg;
    }

    public int getOverSpeedAddMsgLocateType() {
        return overSpeedAddMsgLocateType;
    }

    public void setOverSpeedAddMsgLocateType(int overSpeedAddMsgLocateType) {
        this.overSpeedAddMsgLocateType = overSpeedAddMsgLocateType;
    }

    public int getOverSpeedAddMsgRoadId() {
        return overSpeedAddMsgRoadId;
    }

    public void setOverSpeedAddMsgRoadId(int overSpeedAddMsgRoadId) {
        this.overSpeedAddMsgRoadId = overSpeedAddMsgRoadId;
    }

    public int getLineAlarmAddMsgType() {
        return lineAlarmAddMsgType;
    }

    public void setLineAlarmAddMsgType(int lineAlarmAddMsgType) {
        this.lineAlarmAddMsgType = lineAlarmAddMsgType;
    }

    public int getLineAlarmAddMsgRoadId() {
        return lineAlarmAddMsgRoadId;
    }

    public void setLineAlarmAddMsgRoadId(int lineAlarmAddMsgRoadId) {
        this.lineAlarmAddMsgRoadId = lineAlarmAddMsgRoadId;
    }

    public int getLineAlarmAddMsgDirection() {
        return lineAlarmAddMsgDirection;
    }

    public void setLineAlarmAddMsgDirection(int lineAlarmAddMsgDirection) {
        this.lineAlarmAddMsgDirection = lineAlarmAddMsgDirection;
    }

    public int getLineDriverTimeAddMsgRoadId() {
        return lineDriverTimeAddMsgRoadId;
    }

    public void setLineDriverTimeAddMsgRoadId(int lineDriverTimeAddMsgRoadId) {
        this.lineDriverTimeAddMsgRoadId = lineDriverTimeAddMsgRoadId;
    }

    public String getLineDriverTimeAddMsgDriverTime() {
        return lineDriverTimeAddMsgDriverTime;
    }

    public void setLineDriverTimeAddMsgDriverTime(String lineDriverTimeAddMsgDriverTime) {
        this.lineDriverTimeAddMsgDriverTime = lineDriverTimeAddMsgDriverTime;
    }

    public int getLineDriverTimeAddMsgResult() {
        return lineDriverTimeAddMsgResult;
    }

    public void setLineDriverTimeAddMsgResult(int lineDriverTimeAddMsgResult) {
        this.lineDriverTimeAddMsgResult = lineDriverTimeAddMsgResult;
    }

    public String getExtendCardSignal() {
        return extendCardSignal;
    }

    public void setExtendCardSignal(String extendCardSignal) {
        this.extendCardSignal = extendCardSignal;
    }

    public String getIoStateBit() {
        return ioStateBit;
    }

    public void setIoStateBit(String ioStateBit) {
        this.ioStateBit = ioStateBit;
    }

    public String getAnalogQuanlity() {
        return analogQuanlity;
    }

    public void setAnalogQuanlity(String analogQuanlity) {
        this.analogQuanlity = analogQuanlity;
    }

    public String getWirelessSignalStrength() {
        return wirelessSignalStrength;
    }

    public void setWirelessSignalStrength(String wirelessSignalStrength) {
        this.wirelessSignalStrength = wirelessSignalStrength;
    }

    public String getGNSSLocateStartTotal() {
        return GNSSLocateStartTotal;
    }

    public void setGNSSLocateStartTotal(String GNSSLocateStartTotal) {
        this.GNSSLocateStartTotal = GNSSLocateStartTotal;
    }

    public int getCustomLength() {
        return customLength;
    }

    public void setCustomLength(int customLength) {
        this.customLength = customLength;
    }

    @Override
    public String toString() {
        return "LocationInfoUploadMsg{" +
                "warningFlagField=" + warningFlagField +
                ", statusField=" + statusField +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", speed=" + speed +
                ", direction=" + direction +
                ", gpsTime='" + gpsTime + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", carMileage=" + carMileage +
                ", oilMass=" + oilMass +
                ", driverRecordSpeed=" + driverRecordSpeed +
                ", manulConfirmEventId=" + manulConfirmEventId +
                ", overSpeedAddMsg=" + overSpeedAddMsg +
                ", overSpeedAddMsgLocateType=" + overSpeedAddMsgLocateType +
                ", overSpeedAddMsgRoadId=" + overSpeedAddMsgRoadId +
                ", lineAlarmAddMsgType=" + lineAlarmAddMsgType +
                ", lineAlarmAddMsgRoadId=" + lineAlarmAddMsgRoadId +
                ", lineAlarmAddMsgDirection=" + lineAlarmAddMsgDirection +
                ", lineDriverTimeAddMsgRoadId=" + lineDriverTimeAddMsgRoadId +
                ", lineDriverTimeAddMsgDriverTime='" + lineDriverTimeAddMsgDriverTime + '\'' +
                ", lineDriverTimeAddMsgResult=" + lineDriverTimeAddMsgResult +
                ", extendCardSignal='" + extendCardSignal + '\'' +
                ", ioStateBit='" + ioStateBit + '\'' +
                ", analogQuanlity='" + analogQuanlity + '\'' +
                ", wirelessSignalStrength='" + wirelessSignalStrength + '\'' +
                ", GNSSLocateStartTotal='" + GNSSLocateStartTotal + '\'' +
                ", customLength=" + customLength +
                ", emergentAlarmWarning=" + emergentAlarmWarning +
                ", overSpeedDriveWarning=" + overSpeedDriveWarning +
                ", tiredDriveWarning=" + tiredDriveWarning +
                ", dangerEarlyWarming=" + dangerEarlyWarming +
                ", gnssFaultWarning=" + gnssFaultWarning +
                ", gnssWireCutWarning=" + gnssWireCutWarning +
                ", gnssWireSnappedWarning=" + gnssWireSnappedWarning +
                ", terminalMainPowerLowWarning=" + terminalMainPowerLowWarning +
                ", terminalMainPowerLostWarning=" + terminalMainPowerLostWarning +
                ", lcdFaultWarning=" + lcdFaultWarning +
                ", tssFaultWarning=" + tssFaultWarning +
                ", cameraFaultWarning=" + cameraFaultWarning +
                ", icCardFaultWarning=" + icCardFaultWarning +
                ", overSpeedEarlyWarming=" + overSpeedEarlyWarming +
                ", tiredEarlyWarming=" + tiredEarlyWarming +
                ", overDriverTimeCountWarning=" + overDriverTimeCountWarning +
                ", stopOvertimeWarning=" + stopOvertimeWarning +
                ", inOutTownWarning=" + inOutTownWarning +
                ", inOutLineWarning=" + inOutLineWarning +
                ", lineDriverTimeWarning=" + lineDriverTimeWarning +
                ", lineDeviateWarning=" + lineDeviateWarning +
                ", vssFaultWarning=" + vssFaultWarning +
                ", oilUnusualWarning=" + oilUnusualWarning +
                ", beStolenWarning=" + beStolenWarning +
                ", illegalStartWarning=" + illegalStartWarning +
                ", illegalShiftWarning=" + illegalShiftWarning +
                ", impactEarlyWarming=" + impactEarlyWarming +
                ", oneSideEarlyWarming=" + oneSideEarlyWarming +
                ", illegalOpenDoorWarning=" + illegalOpenDoorWarning +
                ", accStatus=" + accStatus +
                ", locateStatus=" + locateStatus +
                ", latitudeStatus=" + latitudeStatus +
                ", longitudeStatus=" + longitudeStatus +
                ", besinessStatus=" + besinessStatus +
                ", lanAndLongAddPluginStatus=" + lanAndLongAddPluginStatus +
                ", loadPassengerStatus=" + loadPassengerStatus +
                ", oilRoadStatus=" + oilRoadStatus +
                ", electricRoadStatus=" + electricRoadStatus +
                ", carLookStatus=" + carLookStatus +
                ", door1Status=" + door1Status +
                ", door2Status=" + door2Status +
                ", door3Status=" + door3Status +
                ", door4Status=" + door4Status +
                ", door5Status=" + door5Status +
                ", gPSLocateStatus=" + gPSLocateStatus +
                ", beidouLocateStatus=" + beidouLocateStatus +
                ", gLONASSLocateStatus=" + gLONASSLocateStatus +
                ", GalileoLocateStatus=" + GalileoLocateStatus +
                '}';
    }
}
