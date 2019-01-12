package com.tuocheng.jt808.common;

import java.nio.charset.Charset;

public class TPMSConsts {

    public static final String string_encoding = "GBK";

    public static final Charset string_charset = Charset.forName(string_encoding);
    // 标识位
    public static final int pkg_delimiter = 0x7e;
    // 客户端发呆15分钟后,服务器主动断开连接
    public static int tcp_client_idle_minutes = 30;

    // 终端通用应答
    public static final int msg_id_terminal_common_resp = 0x0001;
    // 终端心跳
    // 终端注册
    // 终端注销
    // 终端鉴权
    // 位置信息汇报
    // 胎压数据透传
    public static final int msg_id_terminal_transmission_tyre_pressure = 0x0600;
    // 查询终端参数应答
    public static final int msg_id_terminal_param_query_resp = 0x0104;

    // 平台通用应答
    public static final int cmd_common_resp = 0x8001;
    // 终端注册应答
    public static final int cmd_terminal_register_resp = 0x8100;
    // 设置终端参数
    public static final int cmd_terminal_param_settings = 0X8103;
    // 查询终端参数
    public static final int cmd_terminal_param_query = 0x8104;

    /**
     * 终端通用应答
     */
    public static final int TERMINAL_COMMON_RESPONE = 0x0001;

    /**
     * 平台通用应答
     */
    public static final int CMD_PLAT_COMMON_RESPONE = 0x8001;

    /**
     * 终端心跳 终端心跳数据消息体为空
     */
    public static final int TERMINAL_HEARTBEAT = 0x0002;

    /**
     * 补传分包请求
     */
    public static final int CMD_REUPLOAD_SUB_PACKAGE = 0x8003;

    /**
     * 终端注册
     */
    public static final int TERMINAL_REGISTER = 0x0100;

    /**
     * 终端注册应答
     */
    public static final int CMD_REGISTER_RESPONSE = 0x8100;

    /**
     * 终端注销 终端注销消息体为空
     */
    public static final int TERMINAL_LOGOUT = 0x0003;

    /**
     * 终端鉴权
     */
    public static final int TERMINAL_AUTHENTICATION = 0x0102;

    /**
     * 设置终端参数
     */
    public static final int CMD_SET_TERMINAL_PARAMS = 0x8103;

    /**
     * 查询终端参数 查询终端参数消息体为空
     */
    public static final int CMD_QUERY_TERMINAL_PARAMS = 0x8104;

    /**
     * 查询指定终端参数
     */
    public static final int CMD_QUERY_SPECIFY_TERMINAL_PARAMS = 0x8106;

    /**
     * 查询终端参数应答
     */
    public static final int QUERY_TERMINAL_PARAMS_RESPONE = 0x0104;

    /**
     * 终端控制
     */
    public static final int CMD_TERMINAL_CONTROL = 0x8105;

    /**
     * 查询终端属性 查询终端属性消息体为空。
     */
    public static final int CMD_QUERY_PROPERTIES = 0x8107;

    /**
     * 查询终端属性应答
     */
    public static final int QUERY_PROPERTIES_RESPONE = 0x0107;

    /**
     * 下发终端升级包
     */
    public static final int CMD_SEND_UPDATE_PACKAGE = 0x8108;

    /**
     * 终端升级结果通知 终端在升级完成并重新连接后使用该命令通知监控中心
     */
    public static final int UPDATE_RESULT_NOTICE = 0x0108;

    /**
     * 位置信息汇报
     */
    public static final int LOCATE_UPLODA = 0x0200;

    /**
     * 位置信息查询
     */
    public static final int CMD_LOCATE_QUERY = 0x8201;

    /**
     * 位置信息查询应答
     */
    public static final int LOCATE_QUERY_RESPONE = 0x0201;

    /**
     * 临时位置跟踪控制
     */
    public static final int CMD_TEMP_LOCATE_CONTROL = 0x8202;

    /**
     * 人工确认报警消息
     */
    public static final int CMD_MANAL_CONFIRM_ALARM = 0x8203;

    /**
     * 文本信息下发
     */
    public static final int CMD_TEXT_TO_TERMINAL = 0x8300;

    /**
     * 事件设置
     */
    public static final int CMD_EVENT_CONFIG = 0x8301;

    /**
     * 事件报告
     */
    public static final int EVENT_REPORT = 0x0301;

    /**
     * 提问下发
     */
    public static final int CMD_QUEST_TO_TERMINAL = 0x8302;

    /**
     * 提问应答
     */
    public static final int QUERY_TO_TERMINAL_RESPONE = 0x0302;

    /**
     * 信息点播菜单设置
     */
    public static final int CMD_MSG_BROADCAST_MENU = 0x8303;

    /**
     * 信息点播/取消
     */
    public static final int MSG_BROADCAST_OR_CANCEL = 0x0303;

    /**
     * 信息服务
     */
    public static final int CMD_MSG_SERVER = 0x8304;

    /**
     * 电话回拨
     */
    public static final int CMD_PHONE_CALLBACK = 0x8400;

    /**
     * 设置电话本
     */
    public static final int CMD_PHONEBOOK_CONFIG = 0x8401;

    /**
     * 车辆控制
     */
    public static final int CMD_CAR_CONTROL = 0x8500;

    /**
     * 车辆控制应答
     */
    public static final int CAR_CONTROL_RESPONE = 0x0500;

    /**
     * 设置圆形区域
     */
    public static final int CMD_CIRCAL_CONFIG = 0x8600;

    /**
     * 删除圆形区域
     */
    public static final int CMD_DEL_CIRCAL_CONFIG = 0x8601;

    /**
     * 设置矩形区域
     */
    public static final int CMD_RECTANGLE_CONFIG = 0x8602;

    /**
     * 删除矩形区域
     */
    public static final int CMD_DEL_RECTANGLE_CONFIG = 0x8603;

    /**
     * 设置多边形区域
     */
    public static final int CMD_POLYGON_CONFIG = 0x8604;

    /**
     * 删除多边形区域
     */
    public static final int CMD_DEL_POLYGON_CONFIG = 0x8605;

    /**
     * 设置路线
     */
    public static final int CMD_LINE_CONFIG = 0x8606;

    /**
     * 删除路线
     */
    public static final int CMD_DEL_LINE_CONFIG = 0x8607;

    /**
     * 行驶记录数据采集命令
     */
    public static final int CMD_COLLECTION_RECORD = 0x8700;

    /**
     * 行驶记录数据上传
     */
    public static final int UPLOAD_RECORD = 0x0700;

    /**
     * 行驶记录参数下传命令
     */
    public static final int CMD_RECORD_PARAMS_TO_TERMINAL = 0x8701;

    /**
     * 电子运单上报
     */
    public static final int ELECTIC_WAYBILL_REPORT = 0x0701;

    /**
     * 上报驾驶员身份信息请求 上报驾驶员身份信息请求消息体为空
     */
    public static final int CMD_REQUEST_UPLOAD_DRIVER_CARDID = 0x8702;

    /**
     * 驾驶员身份信息采集上报 终端从业资格证IC卡插入或拔出后,自动触发本指令。收到0x8702指令后,使用本指令
     * 应答
     */
    public static final int COLLECTION_DRIVER_CARDID = 0x0702;

    /**
     * 定位数据批量上传
     */
    public static final int LOCATES_BAT_UPLOAD = 0x0704;

    /**
     * CAN 总线数据上传
     */
    public static final int CAN_DATA_UPLOAD = 0x0705;

    /**
     * 多媒体事件信息上传
     */
    public static final int CMD_MEDIA_EVENT_UPLOAD = 0x0800;

    /**
     * 多媒体数据上传
     */
    public static final int MEDIA_DATA_UPLOAD = 0x0801;

    /**
     * 多媒体数据上传应答
     */
    public static final int MEDIA_DATA_UPLOAD_RESPONE = 0x8800;

    /**
     * 摄像头立即拍摄命令
     */
    public static final int CMD_CAMERA_CATCH = 0x8801;

    /**
     * 摄像头立即拍摄命令应答
     */
    public static final int CMD_CAMERA_CATCH_RESPONE = 0x0805;

    /**
     * 存储多媒体数据检索
     */
    public static final int QUERY_STORAGE_MEDIA_DATA = 0x8802;

    /**
     * 存储多媒体数据检索应答
     */
    public static final int QUERY_STORAGE_MEDIA_DATA_RESPONE = 0x0802;

    /**
     * 存储多媒体数据上传命令
     */
    public static final int CMD_STORAGE_MEDIA_DATA_UPLOAD = 0x8803;

    /**
     * 录音开始命令
     */
    public static final int CMD_START_TAPE = 0x8804;

    /**
     * 单条存储多媒体数据检索上传命令
     */
    public static final int CMD_SINGLE_STORAGE_DATA_UPLOAD = 0x8805;

    /**
     * 数据下行透传
     */
    public static final int DATA_DOWNSTREAM_TRANSMISSTION = 0x8900;

    /**
     * 数据上行透传
     */
    public static final int DATA_UPSTREAM_TRANSMISSTION = 0x0900;

    /**
     * 数据压缩上报
     */
    public static final int DATA_RAR_UPLOAD = 0x0901;

    /**
     * 平台 RSA 公钥
     */
    public static final int PLAT_RSA_COMMON_KEY = 0x8A00;

    /**
     * 终端 RSA 公钥
     */
    public static final int TERMINAL_RSA_COMMON_KEY = 0x0A00;


}
