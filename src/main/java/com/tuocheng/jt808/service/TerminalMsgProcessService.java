package com.tuocheng.jt808.service;

import com.tuocheng.jt808.util.MsgEncoderUtils;
import com.tuocheng.jt808.vo.MsgHeader;
import com.tuocheng.jt808.vo.req.LocationInfoUploadMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.tuocheng.jt808.server.SessionManager;
import com.tuocheng.jt808.vo.PackageData;
import com.tuocheng.jt808.vo.Session;
import com.tuocheng.jt808.vo.req.TerminalAuthenticationMsg;
import com.tuocheng.jt808.vo.req.TerminalRegisterMsg;
import com.tuocheng.jt808.vo.resp.ServerCommonRespMsgBody;
import com.tuocheng.jt808.vo.resp.TerminalRegisterMsgRespBody;

/**
 * 1) 808部标终端【注册】业务处理方法 <br/>
 * 2) 808部标终端【鉴权】业务处理方法 <br>
 * 3) 808部标终端【心跳】业务处理方法 <br>
 * 4) 808部标终端【注销】业务处理方法 <br/>
 * 5) 808部标终端【位置信息汇报】业务处理方法 <br/>
 * 6) 808部标终端【批量位置信息汇报】业务处理方法 <br/>
 */
public class TerminalMsgProcessService extends BaseMsgProcessService {

    private final Logger LOGGER = LoggerFactory.getLogger(TerminalMsgProcessService.class);
    private SessionManager sessionManager;

    public TerminalMsgProcessService() {
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * 1) 808部标终端【注册】业务处理方法 <br>
     *
     *
     * @param msg 终端注册数据包
     * @throws Exception 抛出异常
     */
    public void processRegisterMsg(TerminalRegisterMsg msg) throws Exception {
        LOGGER.debug("终端注册:{}", JSON.toJSONString(msg, true));
        //1.获取终端与平台通讯Session的id
        final String sessionId = Session.buildId(msg.getChannel());
        //2.根据Session会话标识获取Session,如果为空,则根据终端手机号进行创建Session
        Session session = sessionManager.findBySessionId(sessionId);
        if (session == null) {
            session = Session.buildSession(msg.getChannel(), msg.getMsgHeader().getTerminalPhone());
        }
        //2.1 session设置鉴权状态
        session.setAuthenticated(true);
        //2.2 session设置终端手机号
        session.setTerminalPhone(msg.getMsgHeader().getTerminalPhone());
        //2.3 将Session放入Session管理容器
        sessionManager.put(session.getId(), session);
        //3.创建平台通用应该消息体
        TerminalRegisterMsgRespBody respMsgBody = new TerminalRegisterMsgRespBody();
        respMsgBody.setReplyCode(TerminalRegisterMsgRespBody.success);
        respMsgBody.setReplyFlowId(msg.getMsgHeader().getFlowId());
        // TODO 鉴权码暂时写死
        respMsgBody.setReplyToken("111111");
        //4.获取流水号
        int flowId = super.getFlowId(msg.getChannel());
        //5.生成平台通用应答字节数组，并转义
        byte[] bs = MsgEncoderUtils.encode4TerminalRegisterResp(msg, respMsgBody, flowId);
        //6.将应答信息发送到终端
        super.send2Client(msg.getChannel(), bs);
    }

    /**
     * 2) 808部标终端【鉴权】业务处理方法 <br>
     *
     * @param msg
     * @throws Exception
     */
    public void processAuthMsg(TerminalAuthenticationMsg msg) throws Exception {
        // TODO 暂时每次鉴权都成功

        LOGGER.debug("终端鉴权:{}", JSON.toJSONString(msg, true));
        //1.获取终端与平台通讯Session的id
        final String sessionId = Session.buildId(msg.getChannel());
        //2.根据Session会话标识获取Session,如果为空,则根据终端手机号进行创建Session
        Session session = sessionManager.findBySessionId(sessionId);
        if (session == null) {
            session = Session.buildSession(msg.getChannel(), msg.getMsgHeader().getTerminalPhone());
        }
        //2.1 session设置鉴权状态
        session.setAuthenticated(true);
        //2.2 session设置终端手机号
        session.setTerminalPhone(msg.getMsgHeader().getTerminalPhone());
        //2.3 将Session放入Session管理容器
        sessionManager.put(session.getId(), session);
        //3.创建平台通用应该消息体
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody();
        respMsgBody.setReplyCode(ServerCommonRespMsgBody.success);
        respMsgBody.setReplyFlowId(msg.getMsgHeader().getFlowId());
        respMsgBody.setReplyId(msg.getMsgHeader().getMsgId());
        //4.获取流水号
        int flowId = super.getFlowId(msg.getChannel());
        //5.生成平台通用应答字节数组，并转义
        byte[] bs = MsgEncoderUtils.encode4ServerCommonRespMsg(msg, respMsgBody, flowId);
        //6.将应答信息发送到终端
        super.send2Client(msg.getChannel(), bs);
    }

    /**
     * 3) 808部标终端【心跳】业务处理方法 <br>
     *
     * @param req
     * @throws Exception
     */
    public void processTerminalHeartBeatMsg(PackageData req) throws Exception {
        LOGGER.debug("心跳信息:{}", JSON.toJSONString(req, true));
        //1.对终端进行应答--平台通用答应
        commonReplyToTerminal(req);
        //2.数据分析入库
        //TODO
    }

    /**
     * 4) 808部标终端【注销】业务处理方法  <br/>
     *
     * @param req
     * @throws Exception
     */
    public void processTerminalLogoutMsg(PackageData req) throws Exception {
        LOGGER.info("终端注销:{}", JSON.toJSONString(req, true));
        //1.对终端进行应答--平台通用答应
        commonReplyToTerminal(req);
        //2.数据分析入库
        //TODO
    }

    /**
     * 5) 808部标终端【位置信息汇报】业务处理方法 <br/>
     *
     * @param req
     * @throws Exception
     */
    public void processLocationInfoUploadMsg(LocationInfoUploadMsg req) throws Exception {
        LOGGER.debug("位置 信息:{}", JSON.toJSONString(req, true));
        //1.对终端进行应答--平台通用答应
        commonReplyToTerminal(req);
        //2.数据分析入库
        //TODO
    }

    /**
     * 6) 808部标终端【位置信息汇报】业务处理方法 <br/>
     *
     * @param req
     * @throws Exception
     */
    public void processLocationInfoBatUploadMsg(LocationInfoUploadMsg req) throws Exception {
        LOGGER.debug("批量位置 信息:{}", JSON.toJSONString(req, true));
        //1.对终端进行应答--平台通用答应
        commonReplyToTerminal(req);
        //2.数据分析入库
        //TODO
    }

    /**
     * 7) 808部标终端【终端通用应答】业务处理方法 <br>
     *
     * @param req
     * @throws Exception
     */
    public void processTerminalCommonReply(PackageData req) throws Exception {
        LOGGER.debug("终端通用应答:{}", JSON.toJSONString(req, true));
        //1.对终端进行应答--平台通用答应
        commonReplyToTerminal(req);
        //2.数据分析入库
        //TODO
    }

    private void commonReplyToTerminal(PackageData req)throws Exception {
        //1.获取消息头
        final MsgHeader reqHeader = req.getMsgHeader();
        //2.创建平台通用应答消息体
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
                ServerCommonRespMsgBody.success);
        //3.获取流水号
        int flowId = super.getFlowId(req.getChannel());
        //4.生成平台通用应答字节数组，并转义
        byte[] bs = MsgEncoderUtils.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
        //5.将应答信息发送到终端
        super.send2Client(req.getChannel(), bs);
    }
}
