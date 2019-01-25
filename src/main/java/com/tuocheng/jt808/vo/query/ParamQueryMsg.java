package com.tuocheng.jt808.vo.query;

import com.tuocheng.jt808.vo.MsgHeader;
import com.tuocheng.jt808.vo.PackageData;

/**
 * =================================
 *
 * @ClassName ParamQueryMsg
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-23 下午1:09
 * @Version v1.0.0
 * ==================================
 */
public class ParamQueryMsg extends PackageData {

    private MsgHeader msgHeader;


    public ParamQueryMsg(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    @Override
    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    @Override
    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }
}
