package cn.hylexus.jt808.service;

import cn.hylexus.jt808.util.HexStringUtils;
import cn.hylexus.jt808.vo.req.LocationInfoUploadMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.hylexus.jt808.server.SessionManager;
import cn.hylexus.jt808.service.codec.MsgEncoder;
import cn.hylexus.jt808.vo.PackageData;
import cn.hylexus.jt808.vo.PackageData.MsgHeader;
import cn.hylexus.jt808.vo.Session;
import cn.hylexus.jt808.vo.req.TerminalAuthenticationMsg;
import cn.hylexus.jt808.vo.req.TerminalRegisterMsg;
import cn.hylexus.jt808.vo.resp.ServerCommonRespMsgBody;
import cn.hylexus.jt808.vo.resp.TerminalRegisterMsgRespBody;

public class TerminalMsgProcessService extends BaseMsgProcessService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private MsgEncoder msgEncoder;
    private SessionManager sessionManager;

    public TerminalMsgProcessService() {
        this.msgEncoder = new MsgEncoder();
        this.sessionManager = SessionManager.getInstance();
    }

    /**
     * 808部标终端【注册】业务处理方法
     *
     * @param msg
     * @throws Exception
     */
    public void processRegisterMsg(TerminalRegisterMsg msg) throws Exception {
        log.debug("终端注册:{}", JSON.toJSONString(msg, true));
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
        //5.消息加密，并转换成字节数组
        byte[] bs = this.msgEncoder.encode4TerminalRegisterResp(msg, respMsgBody, flowId);
        //6.将应答信息发送到终端
        super.send2Client(msg.getChannel(), bs);
    }

    /**
     * 808部标终端【鉴权】业务处理方法
     *
     * @param msg
     * @throws Exception
     */
    public void processAuthMsg(TerminalAuthenticationMsg msg) throws Exception {
        // TODO 暂时每次鉴权都成功

        log.debug("终端鉴权:{}", JSON.toJSONString(msg, true));
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
        //5.消息加密，并转换成字节数组
        byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(msg, respMsgBody, flowId);
        //6.将应答信息发送到终端
        super.send2Client(msg.getChannel(), bs);
    }

    /**
     * 808部标终端【心跳】业务处理方法
     *
     * @param req
     * @throws Exception
     */
    public void processTerminalHeartBeatMsg(PackageData req) throws Exception {
        log.debug("心跳信息:{}", JSON.toJSONString(req, true));
        //1.获取消息头
        final MsgHeader reqHeader = req.getMsgHeader();
        //2.创建平台通用应答消息体
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
                ServerCommonRespMsgBody.success);
        //3.获取流水号
        int flowId = super.getFlowId(req.getChannel());
        //4.消息加密，并转换成字节数组
        byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
        //5.将应答信息发送到终端
        super.send2Client(req.getChannel(), bs);
    }

    /**
     * 808部标终端【注销】业务处理方法
     *
     * @param req
     * @throws Exception
     */
    public void processTerminalLogoutMsg(PackageData req) throws Exception {
        log.info("终端注销:{}", JSON.toJSONString(req, true));
        //1.获取消息头
        final MsgHeader reqHeader = req.getMsgHeader();
        //2.创建平台通用应答消息体
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
                ServerCommonRespMsgBody.success);
        //3.获取流水号
        int flowId = super.getFlowId(req.getChannel());
        //4.消息加密，并转换成字节数组
        byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
        //5.将应答信息发送到终端
        super.send2Client(req.getChannel(), bs);
    }

    /**
     * 808部标终端【位置信息汇报】业务处理方法
     *
     * @param req
     * @throws Exception
     */
    public void processLocationInfoUploadMsg(LocationInfoUploadMsg req) throws Exception {
        log.debug("位置 信息:{}", JSON.toJSONString(req, true));
        //1.获取消息头
        final MsgHeader reqHeader = req.getMsgHeader();
        //2.创建平台通用应答消息体
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
                ServerCommonRespMsgBody.success);
        //3.获取流水号
        int flowId = super.getFlowId(req.getChannel());
        //4.消息加密，并转换成字节数组
        byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
        //5.将应答信息发送到终端
        super.send2Client(req.getChannel(), bs);
    }

    /**
     * 808部标终端【位置信息汇报】业务处理方法
     *
     * @param req
     * @throws Exception
     */
    public void processLocationInfoBatUploadMsg(LocationInfoUploadMsg req) throws Exception {
        log.debug("批量位置 信息:{}", JSON.toJSONString(req, true));
        //1.获取消息头
        final MsgHeader reqHeader = req.getMsgHeader();
        //2.创建平台通用应答消息体
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
                ServerCommonRespMsgBody.success);
        //3.获取流水号
        int flowId = super.getFlowId(req.getChannel());
        //4.消息加密，并转换成字节数组
        byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
        //5.将应答信息发送到终端
        super.send2Client(req.getChannel(), bs);
    }
}
