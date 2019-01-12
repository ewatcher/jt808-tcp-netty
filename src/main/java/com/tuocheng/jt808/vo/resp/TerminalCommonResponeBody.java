package com.tuocheng.jt808.vo.resp;

/**
 * =====================================
 *
 * @ClassName: TerminalCommonResponeBody
 * @Description: 终端通用应答
 * @Author nongfeng
 * @Date create in 19-1-13  上午12:00
 * @Version v1.0.0
 * =======================================
 */
public class TerminalCommonResponeBody {

    /**
     * byte[0~1] 应答流水号(WORD) 对应的平台消息的流水号
     */
    private int flowId;

    /**
     * byte[2~3] 应答 ID(WORD) 对应的平台消息的 ID
     */
    private int replyId;

    /***
     * byte[4] 结果(BYTE) <br>
     * 0：成功/确认 <br/>
     * 1：失败 <br/>
     * 2：消息有误 <br/>
     * 3:不支持 <br/>
     **/
    private byte replyCode;

    public byte getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(byte replyCode) {
        this.replyCode = replyCode;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    @Override
    public String toString() {
        return "TerminalCommonResponeBody{" +
                "flowId=" + flowId +
                ", replyId=" + replyId +
                ", replyCode=" + replyCode +
                '}';
    }
}
