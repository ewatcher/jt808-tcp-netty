package com.tuocheng.jt808.util;

import com.tuocheng.jt808.vo.MsgHeader;
import com.tuocheng.jt808.vo.PackageData;
import com.tuocheng.jt808.vo.req.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
 *
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
        int checkSumInPkg = data[data.length - 1];
        //将终端字节数组进行BCC校验，如果不一致输出到日志中
        int calculatedCheckSum = BitUtils.getCheckSum4JT808(data, 0, data.length - 2);
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
        ret.setWarningFlagField(ByteUtils.parseIntFromBytes(data, 0, 3));
        // 2.2. byte[4-7] 状态(DWORD(32))
        ret.setStatusField(ByteUtils.parseIntFromBytes(data, 4, 4));
        // 2.3. byte[8-11] 纬度(DWORD(32)) 以度为单位的纬度值乘以10^6，精确到百万分之一度
        LOGGER.info("纬度：{}",ByteUtils.parseIntFromBytes(data, 8, 4));
        ret.setLatitude(parseIntToLocateData(ByteUtils.parseIntFromBytes(data, 8, 4)));
        // 2.4. byte[12-15] 经度(DWORD(32)) 以度为单位的经度值乘以10^6，精确到百万分之一度
        LOGGER.info("经度：{}",ByteUtils.parseIntFromBytes(data, 12, 4));
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

        //2.返回解析后的位置数据LocationInfoUploadMsg
        return ret;
    }

    //将纬度经度的整形值转换为double
    private static double parseIntToLocateData(int d) {
        BigDecimal d1 = new BigDecimal(Integer.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1000000));
        // 四舍五入,保留6位小数
        return d1.divide(d2,6,BigDecimal.ROUND_HALF_UP).doubleValue();
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
        TerminalCommonResponeBody msgBody=new TerminalCommonResponeBody();
        msgBody.setFlowId(ByteUtils.parseIntFromBytes(data,0,2));
        msgBody.setReplyId(ByteUtils.parseIntFromBytes(data,2,2));
        msgBody.setReplyCode(ByteUtils.parseIntFromBytes(data,4,1));
        //3.返回解析后的位置数据LocationInfoUploadMsg
        return msgBody;
    }

    /**
     *  解析查询终端属性应答信息
     * @param packageData
     * @return
     */
    public static TerminalPropertiesReplyMsg toTerminalPropertiesReplyMsg(PackageData packageData){
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
