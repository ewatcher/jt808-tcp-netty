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
     *侧翻报警参数设置:侧翻角度,单位 1 度,默认为 30 度。 WORD
     */
    public static final int PARAM_ONSIDE_ALARM_CONFIG = 0x005E;


}
