package com.tuocheng.jt808.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuocheng.jt808.util.*;
import com.tuocheng.jt808.vo.MsgHeader;
import com.tuocheng.jt808.vo.req.*;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuocheng.jt808.common.TPMSConsts;
import com.tuocheng.jt808.server.SessionManager;
import com.tuocheng.jt808.service.TerminalMsgProcessService;
import com.tuocheng.jt808.vo.PackageData;
import com.tuocheng.jt808.vo.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)

    private final Logger LOGGER = LoggerFactory.getLogger(TCPServerHandler.class);

    private final SessionManager sessionManager;
    private TerminalMsgProcessService msgProcessService;

    public TCPServerHandler() {
        this.sessionManager = SessionManager.getInstance();
        this.msgProcessService = new TerminalMsgProcessService();
    }

    /**
     * 读取终端传递过来的数据
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf buf = (ByteBuf) msg;
            if (buf.readableBytes() <= 0) {
                //ReferenceCountUtil.safeRelease(msg);
                return;
            }

            byte[] bs = new byte[buf.readableBytes()];
            buf.readBytes(bs);
            //接收到的终端数据包进行转义
            try {
                LOGGER.info("---->begin data is:" + HexStringUtils.toHexString(bs));
                byte[] bsLast = JT808ProtocolUtils.doEscape4Receive(bs, 0, bs.length);
                // 字节数据转换为针对于808消息结构的实体类
                PackageData pkg = MsgDecoderUtils.bytes2PackageData(bsLast);

                // 引用channel,以便回送数据给硬件
                pkg.setChannel(ctx.channel());
                //对接收到的数据进行处理
                this.processPackageData(pkg);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("收到的终端数据包进行转义,发生错误，原因：" + e.toString());
            }

        } finally {
            release(msg);
        }
    }

    /**
     * 接收终端数据包处理业务逻辑
     *
     * @param packageData 终端数据包
     */
    private void processPackageData(PackageData packageData) {
        final MsgHeader header = packageData.getMsgHeader();
        //根据数据包标识调用分发业务逻辑
        switch (header.getMsgId()) {
            //1.终端通用应答
            case TPMSConsts.TERMINAL_COMMON_RESPONE: {
                terminalCommonRespone(packageData, header);
                break;
            }
            //2. 终端【心跳】业务处理
            case TPMSConsts.TERMINAL_HEARTBEAT: {
                terminalHeartbeat(packageData, header);
                break;
            }
            //3. 终端【注册】业务处理
            case TPMSConsts.TERMINAL_REGISTER: {
                terminalRegister(packageData, header);
                break;
            }
            //4. 终端【注销】业务处理
            case TPMSConsts.TERMINAL_LOGOUT: {
                terminalLogout(packageData, header);
                break;
            }
            //5. 终端【鉴权】业务处理
            case TPMSConsts.TERMINAL_AUTHENTICATION: {
                terminalAuthentication(packageData, header);
                break;
            }
            //6.查询终端参数应答
            case TPMSConsts.QUERY_TERMINAL_PARAMS_RESPONE: {
                //TODO
                terminalQueryAnswer(packageData, header);
                break;
            }
            //7.查询终端属性应答
            case TPMSConsts.QUERY_PROPERTIES_RESPONE: {
                //TODO
                terminalQueryPropertiesReply(packageData, header);
                break;
            }
            //8.终端升级结果通知
            case TPMSConsts.UPDATE_RESULT_NOTICE: {
                //TODO
                LOGGER.info(">>>>>>[终端升级结果通知]:" + header.getMsgId());
                LOGGER.info("<<<<<<[终端升级结果通知]:" + header.getMsgId());
                break;
            }
            //9. 终端【位置信息汇报】业务处理
            case TPMSConsts.LOCATE_UPLODA: {
                terminalLocateUpload(packageData, header);
                break;
            }
            //10.位置信息查询应答
            case TPMSConsts.LOCATE_QUERY_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[位置信息查询应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[位置信息查询应答]:" + header.getMsgId());
                break;
            }
            //11.事件报告
            case TPMSConsts.EVENT_REPORT: {
                //TODO
                LOGGER.info(">>>>>>[事件报告]:" + header.getMsgId());
                LOGGER.info("<<<<<<[事件报告]:" + header.getMsgId());
                break;
            }
            //12.提问应答
            case TPMSConsts.QUERY_TO_TERMINAL_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[提问应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[提问应答]:" + header.getMsgId());
                break;
            }
            //13.信息点播/取消
            case TPMSConsts.MSG_BROADCAST_OR_CANCEL: {
                //TODO
                LOGGER.info(">>>>>>[信息点播/取消]:" + header.getMsgId());
                LOGGER.info("<<<<<<[信息点播/取消]:" + header.getMsgId());
                break;
            }
            //14.车辆控制应答
            case TPMSConsts.CAR_CONTROL_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[车辆控制应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[车辆控制应答]:" + header.getMsgId());
                break;
            }
            //15.行驶记录数据上传
            case TPMSConsts.UPLOAD_RECORD: {
                //TODO
                LOGGER.info(">>>>>>[行驶记录数据上传]:" + header.getMsgId());
                LOGGER.info("<<<<<<[行驶记录数据上传]:" + header.getMsgId());
                break;
            }
            //16.电子运单上报
            case TPMSConsts.ELECTIC_WAYBILL_REPORT: {
                //TODO
                LOGGER.info(">>>>>>[电子运单上报]:" + header.getMsgId());
                LOGGER.info("<<<<<<[电子运单上报]:" + header.getMsgId());
                break;
            }
            //17.驾驶员身份信息采集上报
            case TPMSConsts.COLLECTION_DRIVER_CARDID: {
                //TODO
                LOGGER.info(">>>>>>[驾驶员身份信息采集上报]:" + header.getMsgId());
                LOGGER.info("<<<<<<[驾驶员身份信息采集上报]:" + header.getMsgId());
                break;
            }
            //18.终端【位置信息批量上传】
            case TPMSConsts.LOCATES_BAT_UPLOAD: {
                terminalLocateBatUpload(packageData, header);
                break;
            }
            //19.CAN 总线数据上传
            case TPMSConsts.CAN_DATA_UPLOAD: {
                //TODO
                LOGGER.info(">>>>>>[CAN 总线数据上传]:" + header.getMsgId());
                LOGGER.info("<<<<<<[CAN 总线数据上传]:" + header.getMsgId());
                break;
            }
            //20.多媒体数据上传应答
            case TPMSConsts.MEDIA_DATA_UPLOAD_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[多媒体数据上传应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[多媒体数据上传应答]:" + header.getMsgId());
                break;
            }
            //21.摄像头立即拍摄命令应答
            case TPMSConsts.CMD_CAMERA_CATCH_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[摄像头立即拍摄命令应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[摄像头立即拍摄命令应答]:" + header.getMsgId());
                break;
            }
            //22.存储多媒体数据检索应答
            case TPMSConsts.QUERY_STORAGE_MEDIA_DATA_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[存储多媒体数据检索应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[存储多媒体数据检索应答]:" + header.getMsgId());
                break;
            }
            //23
            default: {
                LOGGER.error(">>>>>>[未知消息类型],phone={},msgId={},package={}", header.getTerminalPhone(), header.getMsgId(),
                        packageData);
            }
        }

    }

    /**
     * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，
     * 即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。
     * 在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
     * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，
     * 比如你可能想在关闭连接之前发送一个错误码的响应消息。
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        LOGGER.error("发生异常:{}", cause.getMessage());
        cause.printStackTrace();
    }

    /**
     * netty 通道激活事件，如终端连接
     * 覆盖channelActive 方法在channel被启用的时候触发（在建立连接的时候）
     * 覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        LOGGER.debug("终端连接:{}", session);
    }

    /**
     * netty通道关闭事件 终端断开连接
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final String sessionId = ctx.channel().id().asLongText();
        Session session = sessionManager.findBySessionId(sessionId);
        this.sessionManager.removeBySessionId(sessionId);
        LOGGER.debug("终端断开连接:{}", session);
        ctx.channel().close();
        // ctx.close();
    }


    /**
     * 用户主动触发事件
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                LOGGER.error("服务器主动断开连接:{}", session);
                ctx.close();
            }
        }
    }


    /**
     * 释放资源
     *
     * @param msg 信息对象
     */
    private void release(Object msg) {
        try {
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=======================================


    private void terminalCommonRespone(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[终端通用应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        try {
            this.msgProcessService.processTerminalCommonReply(packageData);
            TerminalCommonResponeBody terminalCommonResponeBody = MsgDecoderUtils.toTerminalCommonResponeBodyMsg(packageData);
            //TODO 数据入库
            LOGGER.info("[终端通用应答信息入库]{}", terminalCommonResponeBody);
            LOGGER.info("<<<<<[终端通用应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        } catch (Exception e) {
            LOGGER.error("<<<<<[终端通用应答]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 终端注册
     *
     * @param packageData
     * @param header
     */
    private void terminalHeartbeat(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        try {
            this.msgProcessService.processTerminalHeartBeatMsg(packageData);
            LOGGER.info("<<<<<[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        } catch (Exception e) {
            LOGGER.error("<<<<<[终端心跳]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void terminalAuthentication(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        try {
            TerminalAuthenticationMsg authenticationMsg = new TerminalAuthenticationMsg(packageData);
            this.msgProcessService.processAuthMsg(authenticationMsg);
            LOGGER.info("<<<<<[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        } catch (Exception e) {
            LOGGER.error("<<<<<[终端鉴权]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }
    }


    private void terminalQueryAnswer(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>>[查询终端参数应答]:" + header.getMsgId());
        LOGGER.debug("查询终端参数 信息:{}", JSON.toJSONString(packageData, true));
        LOGGER.info("<<<<<<[查询终端参数应答]:" + header.getMsgId());
    }

    private void terminalRegister(PackageData packageData, MsgHeader header) {
        try {
            TerminalRegisterMsg msg = MsgDecoderUtils.toTerminalRegisterMsg(packageData);
            this.msgProcessService.processRegisterMsg(msg);
            LOGGER.info("<<<<<[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        } catch (Exception e) {
            LOGGER.error("<<<<<[终端注册]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void terminalLogout(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        try {
            msgProcessService.processTerminalLogoutMsg(packageData);
            LOGGER.info("<<<<<[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        } catch (Exception e) {
            LOGGER.error("<<<<<[终端注销]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void terminalLocateUpload(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        try {
            LocationInfoUploadMsg locationInfoUploadMsg = MsgDecoderUtils.toLocationInfoUploadMsg(packageData);
            this.msgProcessService.processLocationInfoUploadMsg(locationInfoUploadMsg);
            LOGGER.info("<<<<<[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            //TODO
            //msgProcessService.queryTerminalProperties(packageData);
        } catch (Exception e) {
            LOGGER.error("<<<<<[位置信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void terminalLocateBatUpload(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[批量上传位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        try {
            List<LocationInfoUploadMsg> locationInfoBatUploadMsgs = MsgDecoderUtils.toLocationInfoBatUploadMsg(packageData);
            if(ValidateUtil.isValidListObject(locationInfoBatUploadMsgs)){
                for (LocationInfoUploadMsg locationInfoUploadMsg:locationInfoBatUploadMsgs
                     ) {
                    this.msgProcessService.processLocationInfoUploadMsg(locationInfoUploadMsg);
                }
            }
            LOGGER.info("<<<<<[批量上传位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        } catch (Exception e) {
            LOGGER.error("<<<<<[位批量上传位置信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }

    }


    private void terminalQueryPropertiesReply(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[查询终端属性应答信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        //TODO
        try {
            TerminalPropertiesReplyMsg replyMsg = MsgDecoderUtils.toTerminalPropertiesReplyMsg(packageData);
            this.msgProcessService.processQueryParamsReplyMsg(replyMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("<<<<<[查询终端属性应答信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
    }

}