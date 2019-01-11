package cn.hylexus.jt808.util;

import java.util.Arrays;
import java.util.List;

/**
 * =================================
 *
 * @ClassName BitUtils
 * @Description 位操作輔助类
 * @Author nongfeng
 * @Date 19-1-11 下午6:19
 * @Version v1.0.0
 * ==================================
 */
public class BitUtils {


    /**
     * 把一个整形转换为byte
     *
     * @param value 输入参数
     * @return 返回1个有
     * @throws Exception 异常抛出
     */
    public static byte integerTo1Byte(int value) {
        return (byte) (value & 0xFF);
    }


    /**
     * 把一个整形转换为1位的byte数组
     *
     * @param value 输入参数
     * @return 返回1位的byte数组
     */
    public static byte[] integerTo1Bytes(int value) {
        byte[] result = new byte[1];
        result[0] = (byte) (value & 0xFF);
        return result;
    }


    /**
     * 把一个整形改为2位的byte数组
     *
     * @param value 输入参数
     * @return 返回2位的byte数组
     */
    public static byte[] integerTo2Bytes(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) ((value >>> 8) & 0xFF);
        result[1] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为3位的byte数组
     *
     * @param value 输入参数
     * @return 返回3位的byte数组
     * @throws Exception
     */
    public static byte[] integerTo3Bytes(int value) {
        byte[] result = new byte[3];
        result[0] = (byte) ((value >>> 16) & 0xFF);
        result[1] = (byte) ((value >>> 8) & 0xFF);
        result[2] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为4位的byte数组
     *
     * @param value 输入参数
     * @return
     * @throws Exception
     */
    public static byte[] integerTo4Bytes(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) ((value >>> 24) & 0xFF);
        result[1] = (byte) ((value >>> 16) & 0xFF);
        result[2] = (byte) ((value >>> 8) & 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把byte[]转化位整形,通常为指令用
     *
     * @param value 输入参数
     * @return 返回int
     * @throws Exception
     */
    public static int byteToInteger(byte[] value) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value);
        } else {
            result = fourBytesToInteger(value);
        }
        return result;
    }

    /**
     * 把一个byte转化位整形,通常为指令用
     *
     * @param value 输入参数
     * @return 返回int
     * @throws Exception
     */
    public static int oneByteToInteger(byte value) {
        return (int) value & 0xFF;
    }

    /**
     * 把一个2位的数组转化位整形
     *
     * @param value 输入参数
     * @return 返回int
     * @throws Exception
     */
    public static int twoBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * 把一个3位的数组转化位整形
     *
     * @param value 输入参数
     * @return 返回int
     * @throws Exception
     */
    public static int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * 把一个4位的数组转化位整形,通常为指令用
     *
     * @param value 输入参数
     * @return 返回int
     * @throws Exception
     */
    public static int fourBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个4位的数组转化位整形
     *
     * @param value 输入参数
     * @return 返回long
     * @throws Exception
     */
    public static long fourBytesToLong(byte[] value) throws Exception {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个数组转化长整形
     *
     * @param value 输入参数
     * @return 返回long
     * @throws Exception
     */
    public static long bytes2Long(byte[] value) {
        long result = 0;
        int len = value.length;
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result += (value[i] & 0x0ff);
            } else {
                result += (value[i] & 0x0ff) << temp;
            }
        }
        return result;
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value 输入参数
     * @return 返回byte[]
     * @throws Exception
     */
    public static byte[] longToBytes(long value) {
        return longToBytes(value, 8);
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value 输入参数
     * @return 返回byte[]
     * @throws Exception
     */
    public static byte[] longToBytes(long value, int len) {
        byte[] result = new byte[len];
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result[i] += (value & 0x0ff);
            } else {
                result[i] += (value >>> temp) & 0x0ff;
            }
        }
        return result;
    }

    /**
     * 得到一个消息ID
     *
     * @return 返回byte[]
     * @throws Exception
     */
    public static byte[] generateTransactionID() throws Exception {
        byte[] id = new byte[16];
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 0, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 2, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 4, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 6, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 8, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 10, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 12, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 14, 2);
        return id;
    }

    /**
     * 把IP拆分位int数组
     *
     * @param ip 输入参数
     * @return 返回int[]
     * @throws Exception
     */
    public static int[] getIntIPValue(String ip) throws Exception {
        String[] sip = ip.split("[.]");
        int[] intIP = {Integer.parseInt(sip[0]), Integer.parseInt(sip[1]), Integer.parseInt(sip[2]),
                Integer.parseInt(sip[3])};
        return intIP;
    }

    /**
     * 把byte类型IP地址转化位字符串
     *
     * @param address 输入参数
     * @return 返回String
     * @throws Exception
     */
    public static String getStringIPValue(byte[] address) throws Exception {
        int first = oneByteToInteger(address[0]);
        int second = oneByteToInteger(address[1]);
        int third = oneByteToInteger(address[2]);
        int fourth = oneByteToInteger(address[3]);

        return first + "." + second + "." + third + "." + fourth;
    }

    /**
     * 合并字节数组
     *
     * @param first 输入参数
     * @param rest  输入参数
     * @return
     */
    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    /**
     * 合并字节数组
     *
     * @param rest 输入参数
     * @return
     */
    public static byte[] concatAll(List<byte[]> rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    public static float byte2Float(byte[] bs) {
        return Float.intBitsToFloat(
                (((bs[3] & 0xFF) << 24) + ((bs[2] & 0xFF) << 16) + ((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
    }

    public static float chengByteToFloat(byte[] bs) {
        return Float.intBitsToFloat(
                (((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
    }


    public static float byteBE2Float(byte[] bytes) {
        int l;
        l = bytes[0];
        l &= 0xff;
        l |= ((long) bytes[1] << 8);
        l &= 0xffff;
        l |= ((long) bytes[2] << 16);
        l &= 0xffffff;
        l |= ((long) bytes[3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * 根据JT808协议，将给定的字体数组,开始下标和结束下标生成消息校验码
     *
     * @param bs    终端数据包byte[]数组
     * @param start 数组下标开始
     * @param end   数组下标结束
     * @return 返回整型校验码
     */
    public static int getCheckSum4JT808(byte[] bs, int start, int end) {
        if (start < 0 || end > bs.length) {
            throw new ArrayIndexOutOfBoundsException("getCheckSum4JT808 error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        }
        int cs = 0;
        for (int i = start; i <= end; i++) {
            cs ^= bs[i];
        }
        return cs;
    }

    public static void main(String[] args) {
        byte[] bs = new byte[]{01, 02, 00, 06, 06, 71, 120, 121, 03, 117, 01, 104, 49, 49, 49, 49, 49, 49, 58};
        int a = BitUtils.getCheckSum4JT808(bs, 0, 18);
        System.out.println("----checkNumber:" + a);
    }

    public static int getBitRange(int number, int start, int end) {
        if (start < 0) {
            throw new IndexOutOfBoundsException("min index is 0,but start = " + start);
        }
        if (end >= Integer.SIZE) {
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but end = " + end);
        }
        return (number << Integer.SIZE - (end + 1)) >>> Integer.SIZE - (end - start + 1);
    }

    public  static int getBitAt(int number, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("min index is 0,but " + index);
        }
        if (index >= Integer.SIZE) {
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but " + index);
        }
        return ((1 << index) & number) >> index;
    }

    public static int getBitAtS(int number, int index) {
        String s = Integer.toBinaryString(number);
        return Integer.parseInt(s.charAt(index) + "");
    }

    @Deprecated
    public static int getBitRangeS(int number, int start, int end) {
        String s = Integer.toBinaryString(number);
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < Integer.SIZE) {
            sb.insert(0, "0");
        }
        String tmp = sb.reverse().substring(start, end + 1);
        sb = new StringBuilder(tmp);
        return Integer.parseInt(sb.reverse().toString(), 2);
    }
}
