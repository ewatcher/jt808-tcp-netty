package com.tuocheng.jt808.service.handler;

import com.tuocheng.jt808.util.HexStringUtils;
import com.tuocheng.jt808.util.JT808ProtocolUtils;
import com.tuocheng.jt808.util.MsgDecoderUtils;
import com.tuocheng.jt808.vo.MsgHeader;
import com.tuocheng.jt808.vo.req.LocationInfoUploadMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuocheng.jt808.common.TPMSConsts;
import com.tuocheng.jt808.server.SessionManager;
import com.tuocheng.jt808.service.TerminalMsgProcessService;
import com.tuocheng.jt808.vo.PackageData;
import com.tuocheng.jt808.vo.Session;
import com.tuocheng.jt808.vo.req.TerminalAuthenticationMsg;
import com.tuocheng.jt808.vo.req.TerminalRegisterMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)

    private final Logger LOGGER = LoggerFactory.getLogger(TCPServerHandler.class);

    private final SessionManager sessionManager;
    private TerminalMsgProcessService msgProcessService;

    public TCPServerHandler() {
        this.sessionManager = SessionManager.getInstance();
        this.msgProcessService = new TerminalMsgProcessService();
    }

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
                byte[] bsLast = JT808ProtocolUtils.doEscape4Receive(bs, 0, bs.length);
                // 字节数据转换为针对于808消息结构的实体类
                PackageData pkg = MsgDecoderUtils.bytes2PackageData(bsLast);
                LOGGER.info("---->data from terminal is:" + HexStringUtils.toHexString(bsLast));
                // 引用channel,以便回送数据给硬件
                pkg.setChannel(ctx.channel());
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
                //TODO
                LOGGER.info(">>>>>>[终端通用应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[终端通用应答]:" + header.getMsgId());
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
                LOGGER.info(">>>>>>[查询终端参数应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[查询终端参数应答]:" + header.getMsgId());
                break;
            }
            //7.查询终端属性应答
            case TPMSConsts.QUERY_PROPERTIES_RESPONE: {
                //TODO
                LOGGER.info(">>>>>>[询终端属性应答]:" + header.getMsgId());
                LOGGER.info("<<<<<<[询终端属性应答]:" + header.getMsgId());
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        LOGGER.error("发生异常:{}", cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        LOGGER.debug("终端连接:{}", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final String sessionId = ctx.channel().id().asLongText();
        Session session = sessionManager.findBySessionId(sessionId);
        this.sessionManager.removeBySessionId(sessionId);
        LOGGER.debug("终端断开连接:{}", session);
        ctx.channel().close();
        // ctx.close();
    }

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

    private void release(Object msg) {
        try {
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=======================================

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
        } catch (Exception e) {
            LOGGER.error("<<<<<[位置信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void terminalLocateBatUpload(PackageData packageData, MsgHeader header) {
        LOGGER.info(">>>>>[批量上传位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
        //TODO
        try {
            LocationInfoUploadMsg locationInfoUploadMsg = MsgDecoderUtils.toLocationInfoUploadMsg(packageData);
            this.msgProcessService.processLocationInfoBatUploadMsg(locationInfoUploadMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("<<<<<[批量上传位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
    }


}