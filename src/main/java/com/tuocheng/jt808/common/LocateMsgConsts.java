package com.tuocheng.jt808.common;

/**
 * =================================
 *
 * @ClassName LocateMsgConsts
 * @Description TODO
 * @Author nongfeng
 * @Date 19-2-16 下午1:44
 * @Version v1.0.0
 * ==================================
 */
public class LocateMsgConsts {
    /**
     * 附加信息 里程ID
     */
    public static final int CAR_MAILE_AGE_ID = 0x01;

    /**
     * 附加信息 油量ID
     */
    public static final int OIL_MASS_ID = 0x02;

    /**
     * 附加信息 行驶记录功能获取的速度ID
     */
    public static final int DRIVER_RECORD_SPEED_ID = 0x03;

    /**
     * 附加信息 需要人工确认报警事件的ID
     */
    public static final int MANUL_CONFIRM_EVENT_ID = 0x04;

    /**
     * 附加信息 超速报警附加信息ID
     */
    public static final int OVER_SPEED_ALARM_ID = 0x11;

    /**
     * 附加信息 进出区域/路线报警附加信息ID
     */
    public static final int LINE_ALARM_ID = 0x12;

    /**
     * 附加信息 路段行驶时间不足/过长报警附加信息ID
     */
    public static final int LINE_DRIVER_TIME_ID = 0x13;

    /**
     * 附加信息 扩展车辆信号状态位ID
     */
    public static final int EXTEND_CAR_SIGNAL_ID = 0x25;

    /**
     * 附加信息 IO状态位ID
     */
    public static final int IO_STATUS_ID = 0x2A;

    /**
     * 附加信息 模拟量ID
     */
    public static final int ANALOG_QUANLITY_ID = 0x2B;

    /**
     * 附加信息 无线通信网络信号强度ID
     */
    public static final int WIRELESS_SIGNAL_ID = 0x30;

    /**
     * 附加信息 GNSS 定位卫星数ID
     */
    public static final int GNSS_STARTS_TOTAL_ID = 0x31;

    /**
     * 附加信息 后续自定义信息长度ID
     */
    public static final int CUSTOMER_LENGTH_ID = 0xE0;

}
