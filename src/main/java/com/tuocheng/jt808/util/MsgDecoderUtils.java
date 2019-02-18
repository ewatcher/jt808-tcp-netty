package com.tuocheng.jt808.util;

import com.alibaba.fastjson.JSONObject;
import com.tuocheng.jt808.common.LocateMsgConsts;
import com.tuocheng.jt808.vo.MsgHeader;
import com.tuocheng.jt808.vo.PackageData;
import com.tuocheng.jt808.vo.req.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * =================================
 *
 * @ClassName MsgDecoderUtils
 * @Description 终端数据解码器辅助类 <br/>
 * 1) 通过Netty框架获取终端数据包字节数组后转换与PackageData数据模型; <br/>
 * 2) 截取终端数据包中的消息头信息，并转换成MsgHeader数据模型; <br/>
 * 3) 解析終端注冊消息数据包; <br/>
 * 4) 解析终端位置信息汇报数据包; <br/>
 * 5) ; <br/>
 * 6) ; <br/>
 * @Author nongfeng
 * @Date 19-1-12 下午3:05
 * @Version v1.0.0
 * ==================================
 */
public class MsgDecoderUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgDecoderUtils.class);


    /**
     * 1)通过Netty框架获取终端数据包字节数组后转换与PackageData数据模型
     *
     * @param data 终端数据包
     * @return 返回PackageData
     */
    public static PackageData bytes2PackageData(byte[] data) {
        PackageData ret = new PackageData();

        // 1.构建消息头 16byte 或 12byte
        MsgHeader msgHeader = parseMsgHeaderFromBytes(data);
        ret.setMsgHeader(msgHeader);

        int msgBodyByteStartIndex = 12;
        // 2. 构建消息体
        // 2.1计算消息体的长度，有子包信息,消息体起始字节后移四个字节:消息包总数(word(16))+包序号(word(16))
        if (msgHeader.isHasSubPackage()) {
            msgBodyByteStartIndex = 16;
        }
        //2.2从终端字节数组中截取消息体
        byte[] tmp = new byte[msgHeader.getMsgBodyLength()];
        System.arraycopy(data, msgBodyByteStartIndex, tmp, 0, tmp.length);
        ret.setMsgBodyBytes(tmp);

        // 3.获取校验码， 去掉分隔符之后，最后一位就是校验码
        int checkSumInPkg = (int) data[data.length - 1] & 0xFF;
        //将终端字节数组进行BCC校验，如果不一致输出到日志中
        int calculatedCheckSum = BitUtils.getCheckSum4JT808(data, 0, data.length - 2);
        LOGGER.info("my checkSum:" + calculatedCheckSum + ",hex is:" + Integer.toHexString(calculatedCheckSum).toUpperCase());
        ret.setCheckSum(checkSumInPkg);
        if (checkSumInPkg != calculatedCheckSum) {
            LOGGER.warn("检验码不一致,msgid:{},pkg:{},calculated:{}", msgHeader.getMsgId(), checkSumInPkg, calculatedCheckSum);
        }
        return ret;
    }

    /**
     * 2)截取终端数据包中的消息头信息，并转换成MsgHeader数据模型
     *
     * @param data 终端数据包
     * @return 返回MsgHeader
     */
    public static MsgHeader parseMsgHeaderFromBytes(byte[] data) {
        MsgHeader msgHeader = new MsgHeader();

        // 1. 消息ID word(16)
        msgHeader.setMsgId(ByteUtils.parseIntFromBytes(data, 0, 2));

        // 2. 消息体属性 word(16)=======消息体属性begin==========>
        //2.1配置消息体属性值
        int msgBodyProps = ByteUtils.parseIntFromBytes(data, 2, 2);
        msgHeader.setMsgBodyPropsField(msgBodyProps);
        //处理技巧：在16个字节中获取的属性（长度，加密，子包，保留）位设置为1,后用16进制表示当前属性
        // （长度：0x3FF，加密:0x1c00，子包:0x2000，保留:0xc000）,
        // 2.2 [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
        msgHeader.setMsgBodyLength(msgBodyProps & 0x3ff);
        // 2.3 [10-12] 0001,1100,0000,0000(1C00)(加密类型)
        msgHeader.setEncryptionType((msgBodyProps & 0x1c00) >> 10);
        // 2.4 [ 13_ ] 0010,0000,0000,0000(2000)(是否有子包)
        msgHeader.setHasSubPackage(((msgBodyProps & 0x2000) >> 13) == 1);
        // 2.5 [14-15] 1100,0000,0000,0000(C000)(保留位)
        msgHeader.setReservedBit(((msgBodyProps & 0xc000) >> 14) + "");
        // <=========消息体属性end========

        // 3. 终端手机号 bcd[6]
        msgHeader.setTerminalPhone(ByteUtils.parseBcdStringFromBytes(data, 4, 6));

        // 4. 消息流水号 word(16) 按发送顺序从 0 开始循环累加
        msgHeader.setFlowId(ByteUtils.parseIntFromBytes(data, 10, 2));

        // 5. 消息包封装项
        // 有子包信息
        if (msgHeader.isHasSubPackage()) {
            // 消息包封装项字段
            msgHeader.setPackageInfoField(ByteUtils.parseIntFromBytes(data, 12, 4));
            // byte[0-1] 消息包总数(word(16))
            msgHeader.setTotalSubPackage(ByteUtils.parseIntFromBytes(data, 12, 2));

            // byte[2-3] 包序号(word(16)) 从 1 开始
            msgHeader.setSubPackageSeq(ByteUtils.parseIntFromBytes(data, 12, 2));
        }
        return msgHeader;
    }


    /**
     * 3)解析終端注冊消息数据包
     *
     * @param packageData 终端数据包
     * @return 返回TerminalRegisterMsg
     */
    public static TerminalRegisterMsg toTerminalRegisterMsg(PackageData packageData) {
        //1.从終端注冊消息数据包中获取消息体
        TerminalRegisterMsg ret = new TerminalRegisterMsg(packageData);
        byte[] data = ret.getMsgBodyBytes();
        //2.根据JT808部标规定，解析终端注册消息体
        TerminalRegisterMsg.TerminalRegInfo body = new TerminalRegisterMsg.TerminalRegInfo();

        // 2.1. byte[0-1] 省域ID(WORD)
        // 设备安装车辆所在的省域，省域ID采用GB/T2260中规定的行政区划代码6位中前两位
        // 0保留，由平台取默认值
        body.setProvinceId(ByteUtils.parseIntFromBytes(data, 0, 2));

        // 2.2. byte[2-3] 设备安装车辆所在的市域或县域,市县域ID采用GB/T2260中规定的行 政区划代码6位中后四位
        // 0保留，由平台取默认值
        body.setCityId(ByteUtils.parseIntFromBytes(data, 2, 2));

        // 2.3. byte[4-8] 制造商ID(BYTE[5]) 5 个字节，终端制造商编码
        // byte[] tmp = new byte[5];
        body.setManufacturerId(ByteUtils.parseStringFromBytes(data, 4, 5));

        // 2.4. byte[9-16] 终端型号(BYTE[8]) 八个字节， 此终端型号 由制造商自行定义 位数不足八位的，补空格。
        body.setTerminalType(ByteUtils.parseStringFromBytes(data, 9, 8));

        // 2.5. byte[17-23] 终端ID(BYTE[7]) 七个字节， 由大写字母 和数字组成， 此终端 ID由制造 商自行定义
        body.setTerminalId(ByteUtils.parseStringFromBytes(data, 17, 7));

        // 2.6. byte[24] 车牌颜色(BYTE) 车牌颜 色按照JT/T415-2006 中5.4.12 的规定
        body.setLicensePlateColor(ByteUtils.parseIntFromBytes(data, 24, 1));

        // 2.7. byte[25-x] 车牌(STRING) 公安交 通管理部门颁 发的机动车号牌
        body.setLicensePlate(ByteUtils.parseStringFromBytes(data, 25, data.length - 25));
        ret.setTerminalRegInfo(body);
        //3.返回
        return ret;
    }


    public static List<LocationInfoUploadMsg> toLocationInfoBatUploadMsg(PackageData packageData) {
        byte[] msgBodey = packageData.getMsgBodyBytes();
        LOGGER.info("批量信息 消息体:{}", HexStringUtils.toHexString(msgBodey));
        byte[] properties = new byte[5];
        System.arraycopy(msgBodey, 0, properties, 0, 5);
        //数据项目个数
        int itemTotal = ByteUtils.parseIntFromBytes(properties, 0, 2);
        //位置数据类型
        int locateType = ByteUtils.parseIntFromBytes(properties, 2, 1);
        //位置汇报数据体长度
        int locateLength = ByteUtils.parseIntFromBytes(properties, 3, 2);

        BatLocateMsg batLocateMsg = new BatLocateMsg();
        batLocateMsg.setDataItemTotal(itemTotal);
        batLocateMsg.setLocateDataType(locateType);
        batLocateMsg.setLocateDateBodyLength(locateLength);
        LOGGER.info("位置批量信息属性：{}", JSONObject.toJSONString(batLocateMsg, true));
        LOGGER.info("--->msgBodyLength:{}", packageData.getMsgHeader().getMsgBodyLength());

        byte[] locateData = null;
        LocationInfoUploadMsg ret = null;
        List<LocationInfoUploadMsg> retLists = new ArrayList<LocationInfoUploadMsg>();
        int startIndex=0;
        for (int i = 0; i < itemTotal; i++) {
            locateData = new byte[locateLength];
            if (0 == i) {
                System.arraycopy(msgBodey, 5, locateData, 0, locateLength);
            } else {
                if(1==i){
                    //5+60+2
                    startIndex+=67;
                }else{
                    //60+2
                    startIndex+=62;
                }
                System.arraycopy(msgBodey, startIndex, locateData, 0, locateLength);
            }

            LOGGER.info("位置信息{}：{},", i, HexStringUtils.toHexString(locateData));
            LOGGER.info("位置信息{}：startIndex:{},", i, startIndex);
            //解析数据信息
            PackageData packageDataRet = new PackageData();
            packageDataRet.setChannel(packageData.getChannel());
            packageDataRet.setCheckSum(packageData.getCheckSum());
            MsgHeader msgHeader = packageData.getMsgHeader();
            msgHeader.setMsgBodyLength(locateLength);
            packageDataRet.setMsgHeader(msgHeader);
            packageDataRet.setMsgBodyBytes(locateData);
            ret = new LocationInfoUploadMsg(packageDataRet);
            LOGGER.info("LocationInfoUploadMsg {}：{}", i, ret);
            //2.解析参数
            // 2.1  byte[0-3] 报警标志(DWORD(32))
            int state = ByteUtils.parseIntFromBytes(locateData, 0, 4);
            //解析报警状态
            ret.setWarningFlagField(state);
            configAlarmState(ret, state);

            // 2.2. byte[4-7] 状态(DWORD(32))
            int statusState = ByteUtils.parseIntFromBytes(locateData, 4, 4);
            ret.setStatusField(statusState);
            //解析状态
            configStatus(ret, statusState);

            // 2.3. byte[8-11] 纬度(DWORD(32)) 以度为单位的纬度值乘以10^6，精确到百万分之一度
            ret.setLatitude(parseIntToLocateData(ByteUtils.parseIntFromBytes(locateData, 8, 4)));
            // 2.4. byte[12-15] 经度(DWORD(32)) 以度为单位的经度值乘以10^6，精确到百万分之一度
            ret.setLongitude(parseIntToLocateData(ByteUtils.parseIntFromBytes(locateData, 12, 4)));
            // 2.5. byte[16-17] 高程(WORD(16)) 海拔高度，单位为米（ m）
            ret.setElevation(ByteUtils.parseIntFromBytes(locateData, 16, 2));
            // 2.6 byte[18-19] 速度(WORD) 1/10km/h
            ret.setSpeed(ByteUtils.parseFloatFromBytes(locateData, 18, 2));
            // 2.7 byte[20-21] 方向(WORD) 0-359，正北为 0，顺时针
            ret.setDirection(ByteUtils.parseIntFromBytes(locateData, 20, 2));
            // 2.8 byte[22-x] 时间(BCD[6]) YY-MM-DD-hh-mm-ss
            byte[] tmp = new byte[6];
            System.arraycopy(locateData, 22, tmp, 0, 6);
            String time = ByteUtils.parseBcdStringFromBytes(locateData, 22, 6);
            ret.setGpsTime("20" + time);
            ret.setUploadTime(MyDateUtils.longToString(System.currentTimeMillis(), "YYYYMMddHHmmss"));
            //解析附加消息
          //  configLoadAddMsg(ret, locateData);
            retLists.add(ret);


        }
        if (ValidateUtil.isValidListObject(retLists)) {
            return retLists;
        }
        return null;
    }


    /**
     * 4)解析终端位置信息汇报数据包
     *
     * @param packageData 终端数据包
     * @return 返回 LocationInfoUploadMsg
     */
    public static LocationInfoUploadMsg toLocationInfoUploadMsg(PackageData packageData) {
        //1.从终端数据包中截取消息体
        LocationInfoUploadMsg ret = new LocationInfoUploadMsg(packageData);
        final byte[] data = ret.getMsgBodyBytes();
        //2.解析参数
        // 2.1  byte[0-3] 报警标志(DWORD(32))
        int state = ByteUtils.parseIntFromBytes(data, 0, 4);
        //解析报警状态
        ret.setWarningFlagField(state);
        configAlarmState(ret, state);

        // 2.2. byte[4-7] 状态(DWORD(32))
        int statusState = ByteUtils.parseIntFromBytes(data, 4, 4);
        ret.setStatusField(statusState);
        //解析状态
        configStatus(ret, statusState);

        // 2.3. byte[8-11] 纬度(DWORD(32)) 以度为单位的纬度值乘以10^6，精确到百万分之一度
        ret.setLatitude(parseIntToLocateData(ByteUtils.parseIntFromBytes(data, 8, 4)));
        // 2.4. byte[12-15] 经度(DWORD(32)) 以度为单位的经度值乘以10^6，精确到百万分之一度
        ret.setLongitude(parseIntToLocateData(ByteUtils.parseIntFromBytes(data, 12, 4)));
        // 2.5. byte[16-17] 高程(WORD(16)) 海拔高度，单位为米（ m）
        ret.setElevation(ByteUtils.parseIntFromBytes(data, 16, 2));
        // 2.6 byte[18-19] 速度(WORD) 1/10km/h
        ret.setSpeed(ByteUtils.parseFloatFromBytes(data, 18, 2));
        // 2.7 byte[20-21] 方向(WORD) 0-359，正北为 0，顺时针
        ret.setDirection(ByteUtils.parseIntFromBytes(data, 20, 2));
        // 2.8 byte[22-x] 时间(BCD[6]) YY-MM-DD-hh-mm-ss
        byte[] tmp = new byte[6];
        System.arraycopy(data, 22, tmp, 0, 6);
        String time = ByteUtils.parseBcdStringFromBytes(data, 22, 6);
        ret.setGpsTime("20" + time);
        ret.setUploadTime(MyDateUtils.longToString(System.currentTimeMillis(), "YYYYMMddHHmmss"));
        //解析附加消息
        configLoadAddMsg(ret, data);
        //2.返回解析后的位置数据LocationInfoUploadMsg
        return ret;
    }

    /**
     * 解析状态信息
     *
     * @param ret
     * @param stateValue
     * @return
     */
    private static LocationInfoUploadMsg configStatus(LocationInfoUploadMsg ret, int stateValue) {
        ret.setAccStatus(BitUtils.getIntWithBit(stateValue, 0));
        ret.setLocateStatus(BitUtils.getIntWithBit(stateValue, 1));
        ret.setLatitudeStatus(BitUtils.getIntWithBit(stateValue, 2));
        ret.setLongitudeStatus(BitUtils.getIntWithBit(stateValue, 3));
        ret.setBesinessStatus(BitUtils.getIntWithBit(stateValue, 4));
        ret.setLanAndLongAddPluginStatus(BitUtils.getIntWithBit(stateValue, 5));
        int bit8 = BitUtils.getIntWithBit(stateValue, 8);
        int bit9 = BitUtils.getIntWithBit(stateValue, 9);
        ret.setLoadPassengerStatus(bit8 * 10 + bit9);
        ret.setOilRoadStatus(BitUtils.getIntWithBit(stateValue, 10));
        ret.setElectricRoadStatus(BitUtils.getIntWithBit(stateValue, 11));
        ret.setCarLookStatus(BitUtils.getIntWithBit(stateValue, 12));
        ret.setDoor1Status(BitUtils.getIntWithBit(stateValue, 13));
        ret.setDoor2Status(BitUtils.getIntWithBit(stateValue, 14));
        ret.setDoor3Status(BitUtils.getIntWithBit(stateValue, 15));
        ret.setDoor4Status(BitUtils.getIntWithBit(stateValue, 16));
        ret.setDoor5Status(BitUtils.getIntWithBit(stateValue, 17));
        ret.setgPSLocateStatus(BitUtils.getIntWithBit(stateValue, 18));
        ret.setBeidouLocateStatus(BitUtils.getIntWithBit(stateValue, 19));
        ret.setgLONASSLocateStatus(BitUtils.getIntWithBit(stateValue, 20));
        ret.setGalileoLocateStatus(BitUtils.getIntWithBit(stateValue, 21));
        return ret;
    }

    /**
     * 解析报警标志信息
     *
     * @param ret
     * @param stateValue
     * @return
     */
    private static LocationInfoUploadMsg configAlarmState(LocationInfoUploadMsg ret, int stateValue) {
        ret.setEmergentAlarmWarning(BitUtils.getIntWithBit(stateValue, 0));
        ret.setOverSpeedDriveWarning(BitUtils.getIntWithBit(stateValue, 1));
        ret.setTiredDriveWarning(BitUtils.getIntWithBit(stateValue, 2));
        ret.setDangerEarlyWarming(BitUtils.getIntWithBit(stateValue, 3));
        ret.setGnssFaultWarning(BitUtils.getIntWithBit(stateValue, 4));
        ret.setGnssWireCutWarning(BitUtils.getIntWithBit(stateValue, 5));
        ret.setGnssWireSnappedWarning(BitUtils.getIntWithBit(stateValue, 6));
        ret.setTerminalMainPowerLowWarning(BitUtils.getIntWithBit(stateValue, 7));
        ret.setTerminalMainPowerLostWarning(BitUtils.getIntWithBit(stateValue, 8));
        ret.setLcdFaultWarning(BitUtils.getIntWithBit(stateValue, 9));
        ret.setTssFaultWarning(BitUtils.getIntWithBit(stateValue, 10));
        ret.setCameraFaultWarning(BitUtils.getIntWithBit(stateValue, 11));
        ret.setIcCardFaultWarning(BitUtils.getIntWithBit(stateValue, 12));
        ret.setOverSpeedEarlyWarming(BitUtils.getIntWithBit(stateValue, 13));
        ret.setTiredEarlyWarming(BitUtils.getIntWithBit(stateValue, 14));
        ret.setOverDriverTimeCountWarning(BitUtils.getIntWithBit(stateValue, 18));
        ret.setStopOvertimeWarning(BitUtils.getIntWithBit(stateValue, 19));
        ret.setInOutTownWarning(BitUtils.getIntWithBit(stateValue, 20));
        ret.setInOutLineWarning(BitUtils.getIntWithBit(stateValue, 21));
        ret.setLineDriverTimeWarning(BitUtils.getIntWithBit(stateValue, 22));
        ret.setLineDeviateWarning(BitUtils.getIntWithBit(stateValue, 23));
        ret.setVssFaultWarning(BitUtils.getIntWithBit(stateValue, 24));
        ret.setOilUnusualWarning(BitUtils.getIntWithBit(stateValue, 25));
        ret.setBeStolenWarning(BitUtils.getIntWithBit(stateValue, 26));
        ret.setIllegalStartWarning(BitUtils.getIntWithBit(stateValue, 27));
        ret.setIllegalShiftWarning(BitUtils.getIntWithBit(stateValue, 28));
        ret.setImpactEarlyWarming(BitUtils.getIntWithBit(stateValue, 29));
        ret.setOneSideEarlyWarming(BitUtils.getIntWithBit(stateValue, 30));
        ret.setIllegalOpenDoorWarning(BitUtils.getIntWithBit(stateValue, 31));
        return ret;
    }

    /**
     * 解析附加消息
     *
     * @param ret
     * @param data
     * @return
     */
    private static LocationInfoUploadMsg configLoadAddMsg(LocationInfoUploadMsg ret, byte[] data) {
        //消息体附加信息：位置信息附加信息的起始位置
        int startIndex = 28;
        //消息体长度
        int msgBodyLength = ret.getMsgHeader().getMsgBodyLength();
        for (int i = 28; startIndex < msgBodyLength; i++) {
            //附加消息ID
            int msgId = ByteUtils.parseIntFromBytes(data, startIndex, 1);
            //消息内容长度
            int msgLength = ByteUtils.parseIntFromBytes(data, startIndex + 1, 1);
            //根据消息ID解析附加信息内容
            switch (msgId) {
                // 里程
                case LocateMsgConsts
                        .CAR_MAILE_AGE_ID: {
                    ret.setCarMileage(ByteUtils.parseFloatFromBytes(data, startIndex + 2, msgLength));
                    break;
                }
                //行驶记录功能获取的速度
                case LocateMsgConsts
                        .DRIVER_RECORD_SPEED_ID: {
                    ret.setDriverRecordSpeed(ByteUtils.parseFloatFromBytes(data, startIndex + 2, msgLength));
                    break;
                }
                //需要人工确认报警事件的
                case LocateMsgConsts
                        .MANUL_CONFIRM_EVENT_ID: {
                    ret.setManulConfirmEventId(ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength));
                    break;
                }
                //超速报警附加信息
                case LocateMsgConsts
                        .OVER_SPEED_ALARM_ID: {
                    byte[] temp = new byte[msgLength];
                    System.arraycopy(data, startIndex + 2, temp, 0, msgLength);
                    int overSpeedData = ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength);
                    ret.setOverSpeedAddMsg(overSpeedData);
                    //位置类型
                    int locateType = ByteUtils.parseIntFromBytes(temp, 0, 1);
                    ret.setOverSpeedAddMsgLocateType(locateType);
                    //区域或路段 ID
                    int lineID = ByteUtils.parseIntFromBytes(temp, 1, 1);
                    if (0 == locateType) {
                        ret.setOverSpeedAddMsgRoadId(0);
                    } else {
                        ret.setOverSpeedAddMsgRoadId(lineID);
                    }
                    LOGGER.info("------>超速报警附加信息:byte[]=" + HexStringUtils.toHexString(temp));
                    LOGGER.info("------>overSpeedData:" + overSpeedData + ",locateType:" + locateType + ",lineID:" + lineID);
                    break;
                }
                //进出区域/路线报警附加信息
                case LocateMsgConsts
                        .LINE_ALARM_ID: {
                    byte[] temp = new byte[msgLength];
                    System.arraycopy(data, startIndex + 2, temp, 0, msgLength);
                    int lineAlarm = ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength);
                    ret.setLineAlarmAddMsg(lineAlarm);
                    //位置类型
                    int locateType = ByteUtils.parseIntFromBytes(temp, 0, 1);
                    ret.setLineAlarmAddMsgType(locateType);
                    //区域或线路 ID
                    int lineId = ByteUtils.parseIntFromBytes(temp, 1, 4);
                    ret.setLineAlarmAddMsgRoadId(lineId);
                    //方向
                    int direction = ByteUtils.parseIntFromBytes(temp, 5, 1);
                    ret.setLineAlarmAddMsgDirection(direction);
                    LOGGER.info("------>进出区域/路线报警附加信息:byte[]=" + HexStringUtils.toHexString(temp));
                    LOGGER.info("------>lineId:" + lineId + ",locateType:" + locateType + ",direction:" + direction);
                    break;
                }
                //路段行驶时间不足/过长报警附加信息
                case LocateMsgConsts
                        .LINE_DRIVER_TIME_ID: {
                    byte[] temp = new byte[msgLength];
                    System.arraycopy(data, startIndex + 2, temp, 0, msgLength);
                    int lineAlarm = ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength);
                    ret.setLineDriverTimeAddMsg(lineAlarm);
                    //路段 ID
                    int lineId = ByteUtils.parseIntFromBytes(temp, 0, 1);
                    ret.setLineDriverTimeAddMsgRoadId(lineId);
                    //路段行驶时间
                    int time = ByteUtils.parseIntFromBytes(temp, 1, 4);
                    ret.setLineDriverTimeAddMsgDriverTime(time + "");
                    //结果
                    int result = ByteUtils.parseIntFromBytes(temp, 5, 2);
                    ret.setLineDriverTimeAddMsgResult(result);
                    LOGGER.info("------>路段行驶时间不足/过长报警附加信息:byte[]=" + HexStringUtils.toHexString(temp));
                    LOGGER.info("------>lineId:" + lineId + ",time:" + time + ",result:" + result);
                    break;
                }
                //扩展车辆信号状态位
                case LocateMsgConsts
                        .EXTEND_CAR_SIGNAL_ID: {
                    int carSignal = ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength);
                    ret.setExtendCardSignal(carSignal + "");
                    //近光灯 0
                    ret.setExtendCardSignalNearLight(BitUtils.getIntWithBit(carSignal, 0) + "");
                    //远光灯信号 1
                    ret.setExtendCardSignalFarLight(BitUtils.getIntWithBit(carSignal, 1) + "");
                    //右转向灯信号 2
                    ret.setExtendCardSignalRightLight(BitUtils.getIntWithBit(carSignal, 2) + "");
                    //左转向灯信号 3
                    ret.setExtendCardSignalLeftLight(BitUtils.getIntWithBit(carSignal, 3) + "");
                    //制动信号 4
                    ret.setExtendCardSignalBrak(BitUtils.getIntWithBit(carSignal, 4) + "");
                    //倒档信号 5
                    ret.setExtendCardSignalRevert(BitUtils.getIntWithBit(carSignal, 5) + "");
                    //雾灯信号 6
                    ret.setExtendCardSignalFogLight(BitUtils.getIntWithBit(carSignal, 6) + "");
                    //示廓灯 7
                    ret.setExtendCardSignalOutlineLight(BitUtils.getIntWithBit(carSignal, 7) + "");
                    //喇叭信号 8
                    ret.setExtendCardSignalSpeeker(BitUtils.getIntWithBit(carSignal, 8) + "");
                    //空调状态 9
                    ret.setExtendCardSignalAirCodition(BitUtils.getIntWithBit(carSignal, 9) + "");
                    //空挡信号 10
                    ret.setExtendCardSignalNeutral(BitUtils.getIntWithBit(carSignal, 10) + "");
                    //缓速器工作 11
                    ret.setExtendCardSignalRetarder(BitUtils.getIntWithBit(carSignal, 11) + "");
                    //ABS 工作 12
                    ret.setExtendCardSignalABS(BitUtils.getIntWithBit(carSignal, 12) + "");
                    //加热器工作 13
                    ret.setExtendCardSignalHeater(BitUtils.getIntWithBit(carSignal, 13) + "");
                    //离合器状态 14
                    ret.setExtendCardSignalClutch(BitUtils.getIntWithBit(carSignal, 14) + "");
                    break;
                }
                //IO状态位
                case LocateMsgConsts
                        .IO_STATUS_ID
                        : {
                    int ioState = ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength);
                    ret.setIoStateBit(ioState + "");
                    //深度休眠状态
                    ret.setIoStateDeepDormant(BitUtils.getIntWithBit(ioState, 0) + "");
                    //休眠状态
                    ret.setIoStateBitDormant(BitUtils.getIntWithBit(ioState, 1) + "");
                    break;
                }
                //模拟量
                case LocateMsgConsts
                        .ANALOG_QUANLITY_ID: {
                    ret.setAnalogQuanlity(ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength) + "");
                    break;
                }
                //无线通信网络信号强度
                case LocateMsgConsts
                        .WIRELESS_SIGNAL_ID: {
                    ret.setWirelessSignalStrength(ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength) + "");
                    break;
                }
                //GNSS 定位卫星数
                case LocateMsgConsts
                        .GNSS_STARTS_TOTAL_ID: {
                    ret.setGNSSLocateStartTotal(ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength) + "");
                    break;
                }
                //后续自定义信息长度
                case LocateMsgConsts
                        .CUSTOMER_LENGTH_ID: {
                    ret.setCustomLength(ByteUtils.parseIntFromBytes(data, startIndex + 2, msgLength));
                    break;
                }
                default: {
                    LOGGER.info("execute in default");
                }
            }
            //startIndex 为data[]数组下标地址
            startIndex += msgLength + 2;
        }
        return ret;
    }


    //将纬度经度的整形值转换为double
    private static double parseIntToLocateData(int d) {
        BigDecimal d1 = new BigDecimal(Integer.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1000000));
        // 四舍五入,保留6位小数
        return d1.divide(d2, 6, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 4)解析终端位置信息汇报数据包
     *
     * @param packageData 终端数据包
     * @return 返回 LocationInfoUploadMsg
     */
    public static TerminalCommonResponeBody toTerminalCommonResponeBodyMsg(PackageData packageData) {
        //1.从终端数据包中截取消息体
        TerminalCommonResponeBodyMsg ret = new TerminalCommonResponeBodyMsg(packageData);
        final byte[] data = ret.getMsgBodyBytes();

        //2.解析参数
        TerminalCommonResponeBody msgBody = new TerminalCommonResponeBody();
        msgBody.setFlowId(ByteUtils.parseIntFromBytes(data, 0, 2));
        msgBody.setReplyId(ByteUtils.parseIntFromBytes(data, 2, 2));
        msgBody.setReplyCode(ByteUtils.parseIntFromBytes(data, 4, 1));
        //3.返回解析后的位置数据LocationInfoUploadMsg
        return msgBody;
    }

    /**
     * 解析查询终端属性应答信息
     *
     * @param packageData
     * @return
     */
    public static TerminalPropertiesReplyMsg toTerminalPropertiesReplyMsg(PackageData packageData) {
        //1.从终端数据包中截取消息体
        TerminalPropertiesReplyMsg ret = new TerminalPropertiesReplyMsg(packageData);
        final byte[] data = ret.getMsgBodyBytes();

        //2.解析参数

        // 2.1  终端类型byte[0-1] WORD(16)
        ret.setTerminalType(ByteUtils.parseIntFromBytes(data, 0, 1));
        // 2.2. 制造商ID byte[2-6]   BYTE[5] 5 个字节,终端制造商编码。
        ret.setManufacturer(ByteUtils.parseStringFromBytes(data, 2, 5));
        // 2.3. 终端型号 byte[7-26] BYTE[20] <br/>
        ret.setTerminalModel(ByteUtils.parseStringFromBytes(data, 7, 20));
        // 2.4. 终端 ID byte[27-41] BYTE[7] <br/>
        ret.setTerminalID(ByteUtils.parseStringFromBytes(data, 27, 7));
        // 2.5. 终端 SIM 卡 ICCID byte[42-51] BCD[10] <br/>
        ret.setSIMICCID(ByteUtils.parseBcdStringFromBytes(data, 42, 10));
        // 2.6 终端硬件版本号长度 byte[52] BYTE
        ret.setTerminalType(ByteUtils.parseIntFromBytes(data, 52, 1));

        //3.返回解析后的位置数据TerminalPropertiesReplyMsgs
        return ret;
    }

}
