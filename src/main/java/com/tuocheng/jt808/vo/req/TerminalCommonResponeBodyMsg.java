package com.tuocheng.jt808.vo.req;

import com.tuocheng.jt808.common.TPMSConsts;
import com.tuocheng.jt808.vo.PackageData;

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
public class TerminalCommonResponeBodyMsg extends PackageData {

    private TerminalCommonResponeBody  terminalCommonResponeBody;

    public TerminalCommonResponeBodyMsg() {
    }

    public TerminalCommonResponeBodyMsg(PackageData packageData) {
        this();
        this.channel = packageData.getChannel();
        this.checkSum = packageData.getCheckSum();
        this.msgBodyBytes = packageData.getMsgBodyBytes();
        this.msgHeader = packageData.getMsgHeader();
    }

    public TerminalCommonResponeBody getTerminalCommonResponeBody() {
        return terminalCommonResponeBody;
    }

    public void setTerminalCommonResponeBody(TerminalCommonResponeBody terminalCommonResponeBody) {
        this.terminalCommonResponeBody = terminalCommonResponeBody;
    }

    @Override
    public String toString() {
        return "TerminalCommonResponeBodyMsg{" +
                "terminalCommonResponeBody=" + terminalCommonResponeBody +
                '}';
    }
}
