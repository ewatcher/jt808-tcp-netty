package com.tuocheng.jt808.common;

/**
 * =====================================
 *
 * @ClassName:
 * @Description: 终端参数设置各参数项
 * @Author nongfeng
 * @Date create in 19-1-13  上午6:33
 * @Version v1.0.0
 * =======================================
 */
public class TerminalParamConfigConsts {
    /**
     * 终端心跳发送间隔  单位为秒(s) DWORD
     */
    public static final int PARAM_HEARTBEAT_TIME = 0x0001;

    /**
     * TCP 消息应答超时时间,单位为秒(s) DWORD
     */
    public static final int PARAM_TCP_MSG_REPLY_TIMEOUT = 0x0002;

    /**
     * TCP 消息重传次数 DWORD
     */
    public static final int PARAM_TCP_RESEND_TIMES = 0x0003;

    /**
     * UDP 消息应答超时时间,单位为秒(s) DWORD
     */
    public static final int PARAM_UDP_MSG_REPLY_TIMEOUT = 0x0004;

    /**
     * UDP 消息重传次数 DWORD
     */
    public static final int PARAM_UDP_RESEND_TIMES = 0x0005;

    /**
     * 主服务器 APN,无线通信拨号访问点。若网络制式为 CDMA,则该处为PPP 拨号号码 STRING
     */
    public static final int PARAM_MAIN_SERVER_APN = 0x0010;

    /**
     * 主服务器无线通信拨号用户名 STRING
     */
    public static final int PARAM_MAIN_SERVER_USERNAME = 0x0011;

    /**
     * 主服务器无线通信拨号密码 STRING
     */
    public static final int PARAM_MAIN_SERVER_PASSWORD = 0x0012;

    /**
     * 主服务器地址,IP 或域名 STRING
     */
    public static final int PARAM_MAIN_SERVER_IP = 0x0013;

    /**
     * 备份服务器 APN,无线通信拨号访问点 STRING
     */
    public static final int PARAM_SLAVE_SERVER_APN = 0x0014;

    /**
     * 备份服务器无线通信拨号用户名 STRING
     */
    public static final int PARAM_SLAVE_SERVER_USERNAME = 0x0015;

    /**
     * 备份服务器无线通信拨号密码 STRING
     */
    public static final int PARAM_SLAVE_SERVER_PASSWORD = 0x0016;

    /**
     * 备份服务器地址,IP 或域名 STRING
     */
    public static final int PARAM_SLAVE_SERVER_IP = 0x0017;

    /**
     * 服务器 TCP 端口 DWORD
     */
    public static final int PARAM_TCP_PROT = 0x0018;

    /**
     * 服务器 UDP 端口 DWORD
     */
    public static final int PARAM_UPD_PORT = 0x0019;

    /**
     * 道路运输证 IC 卡认证主服务器 IP 地址或域名 STRING
     */
    public static final int PARAM_TRANSPORT_IC_MAIN_IP = 0x001A;

    /**
     * 道路运输证 IC 卡认证主服务器 TCP 端口 DWORD
     */
    public static final int PARAM_TRANSPORT_IC_MAIN_PORT = 0x001B;

    /**
     * 道路运输证 IC 卡认证主服务器 UDP 端口 DWORD
     */
    public static final int PARAM_TRANSPORT_IC_MAIN_UDP_PORT = 0x001C;

    /**
     * 道路运输证 IC 卡认证备份服务器 IP 地址或域名,端口同主服务器 STRING
     */
    public static final int PARAM_TRANSPORT_IC_SLAVE_IP = 0x001D;

    /**
     * 位置汇报策略,0:定时汇报;1:定距汇报;2:定时和定距汇报 DWORD
     */
    public static final int PARAM_LOCATE_TACTICS = 0x0020;

    /**
     * 位置汇报方案,0:根据 ACC 状态; 1:根据登录状态和 ACC 状态,
     * 先判断登录状态,若登录再根据 ACC 状态 DWORD
     */
    public static final int PARAM_LOCATE_PLAN = 0x0021;

    /**
     * 驾驶员未登录汇报时间间隔,单位为秒(s),>0 DWORD
     */
    public static final int PARAM_DRIVER_NOT_LOGIN_PERIOD = 0x0022;

    /**
     * 休眠时汇报时间间隔,单位为秒(s),>0  DWORD
     */
    public static final int PARAM_DORMANT_PERIOD = 0x0027;

    /**
     * 紧急报警时汇报时间间隔,单位为秒(s),>0 DWORD
     */
    public static final int PARAM_EMERGENT_ALRAM_PERIOD = 0x0028;

    /**
     * 缺省时间汇报间隔,单位为秒(s),>0 DWORD
     */
    public static final int PARAM_DEFAULT_PERIOD = 0x0029;

    /**
     * 缺省距离汇报间隔,单位为米(m),>0 DWORD
     */
    public static final int PARAM_DEFAULT_DISTANCE_PERIOD = 0x002C;

    /**
     * 驾驶员未登录汇报距离间隔,单位为米(m),>0 DWORD
     */
    public static final int PARAM_DRIVER_NOT_LOGIN_DISTANCE_PERIOD = 0x002D;

    /**
     * 休眠时汇报距离间隔,单位为米(m),>0 DWORD
     */
    public static final int PARAM_DORMANT_DISTANCE_PERIOD = 0x002E;

    /**
     * 紧急报警时汇报距离间隔,单位为米(m),>0  DWORD
     */
    public static final int PARAM_EMERGENT_ALRAM_DISTANCE_PERIOD = 0x002F;

    /**
     * 拐点补传角度,<180 DWORD
     */
    public static final int PARAM_INFLEXION_RESEND_ANGLE = 0x0030;

    /**
     * 电子围栏半径(非法位移阈值),单位为米 WORD
     */
    public static final int PARAM_ELECTRIC_PEN_RADIUS = 0x0031;

    /**
     * 监控平台电话号码 STRING
     */
    public static final int PARAM_PLAT_PHONE = 0x0040;

    /**
     * 复位电话号码,可采用此电话号码拨打终端电话让终端复位 STRING
     */
    public static final int PARAM_RESET_BY_PHONE = 0x0041;


    /**
     * 恢复出厂设置电话号码,可采用此电话号码拨打终端电话让终端恢复
     * 出厂设置 STRING
     */
    public static final int PARAM_SET_TERMINAL_RESET_TO_ZERO = 0x0042;

    /**
     * 监控平台 SMS 电话号码 STRING
     */
    public static final int PARAM_PLAT_SMS_PHONE = 0x0043;

    /**
     * 接收终端 SMS 文本报警号码 STRING
     */
    public static final int PARAM_RECIVE_TERMINAL_SMS = 0x0044;

    /**
     * 终端电话接听策略, DWORD 0:自动接听;1:ACC ON 时自动接听,OFF 时手动接听
     */
    public static final int PARAM_PHONE_AUTORECIVE_TACTICS = 0x0045;

    /**
     * 每次最长通话时间,单位为秒(s),0 为不允许通话,0xFFFFFFFF 为不限制 DWORD
     */
    public static final int PARAM_MAX_PHONE_ONLINE = 0x0046;

    /**
     * 当月最长通话时间,单位为秒(s),0 为不允许通话,0xFFFFFFFF 为不限制 DWOR
     */
    public static final int PARAM_MONTH_MAX_PHONE_ONLINE = 0x0047;

    /**
     * 监听电话号码 STRING
     */
    public static final int PARAM_MONITORING_PHONE = 0x0048;

    /**
     * 监管平台特权短信号码 STRING
     */
    public static final int PARAM_PLAT_SPECIAL_SMS_CODE = 0x0049;

    /**
     * 报警屏蔽字,与位置信息汇报消息中的报警标志相对应,相应位为 1则相应报警被屏蔽 DWORD
     */
    public static final int PARAM_SHIELD_ALARM_CHART = 0x0050;

    /**
     * 报警发送文本 SMS 开关,与位置信息汇报消息中的报警标志相对应,相应位为 1 则相应报警时发送文本 SMS
     * DWORD
     */
    public static final int PARAM_ALARM_SEND_SMS_BUTTON = 0x0051;

    /**
     * 报警拍摄开关,与位置信息汇报消息中的报警标志相对应,相应位为1 则相应报警时摄像头拍摄  DWORD
     */
    public static final int PARAM_ALRAM_CAMERA_CATCH_BUTTON = 0x0052;

    /**
     * 报警拍摄存储标志,与位置信息汇报消息中的报警标志相对应,相应位为 1 则对相应报警时拍的照片进行存储,否则实时上传
     * DWORD
     */
    public static final int PARAM_ALARM_CAMERA_STORE_FLAG = 0x0053;

    /**
     * 关键标志,与位置信息汇报消息中的报警标志相对应,相应位为 1 则 对相应报警为关键报警 DWORD
     */
    public static final int PARAM_IMPORTANT_FLAG = 0x0054;

    /**
     * 最高速度,单位为公里每小时(km/h) DWORD
     */
    public static final int PARAM_MAX_SPEED = 0x0055;

    /**
     * 超速持续时间,单位为秒(s) DWORD
     */
    public static final int PARAM_OVER_SPEED_TIME = 0x0056;

    /**
     * 连续驾驶时间门限,单位为秒(s) DWORD
     */
    public static final int PARAM_DRIVER_LIMITED_TIME = 0x0057;

    /**
     * 当天累计驾驶时间门限,单位为秒(s) DWORD
     */
    public static final int PARAM_COUNT_DRIVERTIME_TOTAL = 0x0058;

    /**
     * 最小休息时间,单位为秒(s) DWORD
     */
    public static final int PARAM_MIN_REST_TIME = 0x0059;

    /**
     * 最长停车时间,单位为秒(s) DWORD
     */
    public static final int PARAM_CARD_STOP_MAX_TIMES = 0x005A;

    /**
     * 超速报警预警差值,单位为 1/10Km/h DWORD
     */
    public static final int PARAM_OVER_AND_EARLY_VALUE = 0x005B;

    /**
     * 疲劳驾驶预警差值,单位为秒(s),>0 DWORD
     */
    public static final int PARAM_FATIGUE_DRIVING_VALUE = 0x005C;

    /**
     * 碰撞报警参数设置:WORD <br/>
     * b7-b0: 碰撞时间,单位 4ms; <br/>
     * b15-b8:碰撞加速度,单位 0.1g,设置范围在:0-79 之间,默认为10
     */
    public static final int PARAM_IMPACT_ALARM_PARAM_CONFIG = 0x005D;

    /**
     * 侧翻报警参数设置:侧翻角度,单位 1 度,默认为 30 度。 WORD
     */
    public static final int PARAM_ONSIDE_ALARM_CONFIG = 0x005E;

    /**
     * 定时拍照控制 DWORD
     * ---位--------------定义------------------------------描述及要求-------------- <br/>
     * |   0   |     摄像通道 1 定时拍照开关标志        | 0:不允许; 1:允许
     * ------------------------------------------------------------------------ <br/>
     * |   1   |     摄像通道 2 定时拍照开关标志        | 0:不允许; 1:允许
     * ----------------------------------------------------------------------- <br/>
     * |   2   |     摄像通道 3 定时拍照开关标志        | 0:不允许; 1:允许
     * ------------------------------------------------------------------------<br/>
     * |   3   |     摄像通道 4 定时拍照开关标志        | 0:不允许; 1:允许
     * ------------------------------------------------------------------------- <br/>
     * |   4   |     摄像通道 5 定时拍照开关标志        | 0:不允许; 1:允许
     * -------------------------------------------------------------------------- <br/>
     * |  5-7  |     保留                           |
     * -------------------------------------------------------------------------- <br/>
     * |   8   |     摄像通道 1 定时拍照存储标志        |0:存储; 1:上传
     * -------------------------------------------------------------------------- <br/>
     * |   9   |     摄像通道 2 定时拍照存储标志        |0:存储; 1:上传
     * -------------------------------------------------------------------------- <br/>
     * |  10   |     摄像通道 3 定时拍照存储标志        |0:存储; 1:上传
     * -------------------------------------------------------------------------- <br/>
     * |  11   |     摄像通道 4 定时拍照存储标志        |0:存储; 1:上传
     * --------------------------------------------------------------------------- <br/>
     * |  12   |     摄像通道 5 定时拍照存储标志        |0:存储; 1:上传
     * --------------------------------------------------------------------------- <br/>
     * | 13-15 |     保留                           |
     * --------------------------------------------------------------------------- <br/>
     * |  16   |     定时时间单位                     |0:秒,当数值小于 5 秒时,终端按 5 秒处理;1:分
     * ---------------------------------------------------------------------------- <br/>
     * | 17-31 |     定时时间间隔                     |收到参数设置或重新启动后执行
     * ---------------------------------------------------------------------------- <br/>
     */
    public static final int PARAM_TIMING_CAMERA_CONTROL = 0x0064;

    /**
     * 定距拍照控制 DWORD
     */
    public static final int PARAM_SETING_DISTANCE_CAMERA_CONTROL = 0x0065;

    //0x0066-0x006F 保留

    /**
     * 图像/视频质量,1-10,1 最好 DWORD
     */
    public static final int PARAM_IMAGE_VEDIO_QUALITY = 0x0070;


    /**
     * 亮度,0-255 DWORD
     */
    public static final int PARAM_LIGHT_INTENSITY = 0x0071;


    /**
     * 对比度,0-127  DWORD
     */
    public static final int PARAM_CONTRAST = 0x0072;

    /**
     * 饱和度,0-127 DWORD
     */
    public static final int PARAM_SATURATION_LEVEL = 0x0073;

    /**
     * 色度,0-255 DWORD
     */
    public static final int PARAM_COLOR_LEVEL = 0x0074;

    //0x0075-0x007F

    /**
     * 车辆里程表读数,1/10km DWORD
     */
    public static final int PARAM_ = 0x0080;

    /**
     * 车辆所在的省域 ID WORD
     */
    public static final int PARAM_PROVINCE_ID = 0x0081;

    /**
     * 车辆所在的市域 ID WORD
     */
    public static final int PARAM_CITY_ID = 0x0082;

    /**
     * 公安交通管理部门颁发的机动车号牌 STRING
     */
    public static final int PARAM_CAR_NO = 0x0083;

    /**
     * 车牌颜色,按照 JT/T415-2006 的 5.4.12 BYTE
     */
    public static final int PARAM_CAR_NO_COLOR = 0x0084;

    /**
     * GNSS 定位模式 BYTE <br/>
     * bit0,0:禁用 GPS 定位, 1:启用 GPS 定位; <br/>
     * bit1,0:禁用北斗定位, 1:启用北斗定位 <br/>
     * bit2,0:禁用 GLONASS 定位, 1:启用 GLONASS 定位; <br/>
     * bit3,0:禁用 Galileo 定位, 1:启用 Galileo 定位。 <br/>
     */
    public static final int PARAM_LOCATE_MODE = 0x0090;

    /**
     * GNSS 波特率 BYTE <br/>
     * 0x00:4800;0x01:9600; <br/>
     * 0x02:19200;0x03:38400; <br/>
     * 0x04:57600;0x05:115200。 <br/>
     */
    public static final int PARAM_BAUD_RATE = 0x0091;

    /**
     * GNSS 模块详细定位数据输出频率  BYTE <br/>
     * 0x00:500ms;0x01:1000ms(默认值); <br/>
     * 0x02:2000ms;0x03:3000ms; <br/>
     * 0x04:4000ms。 <br/>
     */
    public static final int PARAM_GNSS_OUTPUT_DATA_FREQUENCY = 0x0092;

    /**
     * GNSS 模块详细定位数据采集频率,单位为秒,默认为 1。 DWORD
     */
    public static final int PARAM_GNSS_COLLECT_FREQUENCY = 0x0093;

    /**
     * GNSS 模块详细定位数据上传方式:BYTE <br/>
     * 0x00,本地存储,不上传(默认值); <br/>
     * 0x01,按时间间隔上传; <br/>
     * 0x02,按距离间隔上传; <br/>
     * 0x0B,按累计时间上传,达到传输时间后自动停止上传; <br/>
     * 0x0C,按累计距离上传,达到距离后自动停止上传; <br/>
     * 0x0D,按累计条数上传,达到上传条数后自动停止上传。 <br/>
     */
    public static final int PARAM_GNSS_UPLOAD_MODE = 0x0094;

    /**
     *GNSS 模块详细定位数据上传设置 DWORD <br/>
     * 上传方式为 0x01 时,单位为秒; <br/>
     * 上传方式为 0x02 时,单位为米; <br/>
     * 上传方式为 0x0B 时,单位为秒; <br/>
     * 上传方式为 0x0C 时,单位为米; <br/>
     * 上传方式为 0x0D 时,单位为条 <br/>
     */
    public static final int PARAM_GNSS_LOCATE_UPLOAD_SET = 0x0095;

    /**
     *CAN 总线通道 1 采集时间间隔(ms),0 表示不采集 DWORD
     */
    public static final int PARAM_CAN_CHANEL1_COLLECT_BEAT= 0x0100;


    /**
     *CAN 总线通道 1 上传时间间隔(s),0 表示不上传 WORD
     */
    public static final int PARAM_CAN_CHANEL1_UPLOAD_BEAT = 0x0101;

    /**
     *CAN 总线通道 2 采集时间间隔(ms),0 表示不采集 DWORD
     */
    public static final int PARAM_CAN_CHANEL2_COLLECT_BEAT = 0x0102;

    /**
     *CAN 总线通道 2 上传时间间隔(s),0 表示不上传 WORD
     */
    public static final int PARAM_CAN_CHANEL2_UPLOAD_BEAT= 0x0103;

    /**
     *CAN 总线 ID 单独采集设置:BYTE[8] <br/>
     * bit63-bit32 表示此 ID 采集时间间隔(ms),0 表示不采集; <br/>
     * bit31 表示 CAN 通道号,0:CAN1,1:CAN2; <br/>
     * bit30 表示帧类型,0:标准帧,1:扩展帧; <br/>
     * bit29 表示数据采集方式,0:原始数据,1:采集区间的计算值; <br/>
     * bit28-bit0 表示 CAN 总线 ID。 <br/>
     */
    public static final int PARAM_CAN_COLLECT_SET= 0x0110;
    //



}
