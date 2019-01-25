package com.tuocheng.jt808.util;

import com.tuocheng.jt808.common.TPMSConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * =================================
 *
 * @ClassName ByteUtils
 * @Description 字节，字节数组操作辅助类
 * @Author nongfeng
 * @Date 19-1-11 下午5:51
 * @Version v1.0.0
 * ==================================
 */
public class ByteUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(ByteUtils.class);


    public static int parseIntFromBytes(byte[] data, int startIndex, int length) {
        try {
            // 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
            final int len = length > 4 ? 4 : length;
            byte[] tmp = new byte[len];
            System.arraycopy(data, startIndex, tmp, 0, len);
            return BitUtils.byteToInteger(tmp);
        } catch (Exception e) {
            LOGGER.error("解析整数出错:{}", e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }





    /**
     * 根据指定的长度，始起位置将字节数组转换成BCD字符串
     *
     * @param data
     * @param startIndex
     * @param lenth
     * @return
     */
    public static String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return BCD8421Utils.bcd2String(tmp);
        } catch (Exception e) {
            LOGGER.error("解析BCD(8421码)出错:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据指定的长度，始起位置将字节数组转换成（十进制）字符串
     *
     * @param data
     * @param startIndex
     * @param lenth
     * @return
     */
    public static String parseStringFromBytes(byte[] data, int startIndex, int lenth) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return new String(tmp, TPMSConsts.string_charset);
        } catch (Exception e) {
            LOGGER.error("解析字符串出错:{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static float parseFloatFromBytes(byte[] data, int startIndex, int length) {
        try {
            // 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
            final int len = length > 4 ? 4 : length;
            byte[] tmp = new byte[len];
            System.arraycopy(data, startIndex, tmp, 0, len);
            //解析速度时只有数组byte[]只有两位
            if (2 == len) {
                return BitUtils.chengByteToFloat(tmp);
            }
            return BitUtils.byte2Float(tmp);
        } catch (Exception e) {
            LOGGER.error("解析浮点数出错:{}", e.getMessage());
            e.printStackTrace();
            return 0.0f;
        }
    }



}
