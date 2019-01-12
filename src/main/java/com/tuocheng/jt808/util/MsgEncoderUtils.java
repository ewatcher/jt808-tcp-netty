package com.tuocheng.jt808.util;

import com.tuocheng.jt808.common.TPMSConsts;
import com.tuocheng.jt808.vo.PackageData;
import com.tuocheng.jt808.vo.Session;
import com.tuocheng.jt808.vo.req.TerminalRegisterMsg;
import com.tuocheng.jt808.vo.resp.ServerCommonRespMsgBody;
import com.tuocheng.jt808.vo.resp.TerminalRegisterMsgRespBody;

import java.util.Arrays;

/**
 * =================================
 *
 * @ClassName MsgEncoderUtils
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-12 下午3:10
 * @Version v1.0.0
 * ==================================
 */
public class MsgEncoderUtils {


    public static byte[] encode4TerminalRegisterResp(TerminalRegisterMsg req, TerminalRegisterMsgRespBody respMsgBody,
                                                     int flowId) throws Exception {
        // 消息体字节数组
        byte[] msgBody = null;
        // 鉴权码(STRING) 只有在成功后才有该字段
        if (respMsgBody.getReplyCode() == TerminalRegisterMsgRespBody.success) {
            msgBody = BitUtils.concatAll(Arrays.asList(
                    // 流水号(2)
                    BitUtils.integerTo2Bytes(respMsgBody.getReplyFlowId()),
                    // 结果
                    new byte[]{respMsgBody.getReplyCode()},
                    // 鉴权码(STRING)
                    respMsgBody.getReplyToken().getBytes(TPMSConsts.string_charset)
            ));
        } else {
            msgBody = BitUtils.concatAll(Arrays.asList(
                    // 流水号(2)
                    BitUtils.integerTo2Bytes(respMsgBody.getReplyFlowId()),
                    // 错误代码
                    new byte[]{respMsgBody.getReplyCode()}
            ));
        }

        // 消息头
        int msgBodyProps = JT808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
        byte[] msgHeader = JT808ProtocolUtils.generateMsgHeader(req.getMsgHeader().getTerminalPhone(),
                TPMSConsts.cmd_terminal_register_resp, msgBody, msgBodyProps, flowId);
        byte[] headerAndBody = BitUtils.concatAll(msgHeader, msgBody);

        // 校验码
        int checkSum = BitUtils.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
        // 连接并且转义
        return doEncode(headerAndBody, checkSum);
    }


    public static byte[] encode4ServerCommonRespMsg(PackageData req, ServerCommonRespMsgBody respMsgBody, int flowId)
            throws Exception {
        byte[] msgBody = BitUtils.concatAll(Arrays.asList(
                // 应答流水号
                BitUtils.integerTo2Bytes(respMsgBody.getReplyFlowId()),
                // 应答ID,对应的终端消息的ID
                BitUtils.integerTo2Bytes(respMsgBody.getReplyId()),
                // 结果
                new byte[]{respMsgBody.getReplyCode()}
        ));

        // 消息头
        int msgBodyProps = JT808ProtocolUtils.generateMsgBodyProps(msgBody.length, 0b000, false, 0);
        byte[] msgHeader = JT808ProtocolUtils.generateMsgHeader(req.getMsgHeader().getTerminalPhone(),
                TPMSConsts.cmd_common_resp, msgBody, msgBodyProps, flowId);
        byte[] headerAndBody = BitUtils.concatAll(msgHeader, msgBody);
        // 校验码
        int checkSum = BitUtils.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
        // 连接并且转义
        return doEncode(headerAndBody, checkSum);
    }

    public static byte[] encode4ParamSetting(byte[] msgBodyBytes, Session session) throws Exception {
        // 消息头
        int msgBodyProps = JT808ProtocolUtils.generateMsgBodyProps(msgBodyBytes.length, 0b000, false, 0);
        byte[] msgHeader = JT808ProtocolUtils.generateMsgHeader(session.getTerminalPhone(),
                TPMSConsts.cmd_terminal_param_settings, msgBodyBytes, msgBodyProps, session.currentFlowId());
        // 连接消息头和消息体
        byte[] headerAndBody = BitUtils.concatAll(msgHeader, msgBodyBytes);
        // 校验码
        int checkSum = BitUtils.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length - 1);
        // 连接并且转义
        return doEncode(headerAndBody, checkSum);
    }

    private static byte[] doEncode(byte[] headerAndBody, int checkSum) throws Exception {
        byte[] noEscapedBytes = BitUtils.concatAll(Arrays.asList(
                // 0x7e
                new byte[]{TPMSConsts.pkg_delimiter},
                // 消息头+ 消息体
                headerAndBody,
                // 校验码
                BitUtils.integerTo1Bytes(checkSum),
                // 0x7e
                new byte[]{TPMSConsts.pkg_delimiter}
        ));
        // 转义
        return JT808ProtocolUtils.doEscape4Send(noEscapedBytes, 1, noEscapedBytes.length - 2);
    }
}
