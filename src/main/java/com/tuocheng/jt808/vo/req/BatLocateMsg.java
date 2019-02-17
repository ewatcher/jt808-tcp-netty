package com.tuocheng.jt808.vo.req;

import com.tuocheng.jt808.vo.PackageData;

import java.util.List;
import java.util.Objects;

/**
 * =================================
 *
 * @ClassName BatLocateMsg
 * @Description 位置批量上传解析数据模型实体类
 * @Author nongfeng
 * @Date 19-2-16 下午12:54
 * @Version v1.0.0
 * ==================================
 */
public class BatLocateMsg extends PackageData {
    /**
     * 标识;
     */
    private String uuid;

    /**
     * 数据项个数
     */
    private int dataItemTotal;

    /**
     * 位置数据类型 0:正常位置批量汇报,1:盲区补报
     */
    private int locateDataType;

    /**
     * 位置汇报数据体长度
     */
    private int locateDateBodyLength;

    /**
     * 位置信息基本信息
     */
    private List<LocationInfoUploadMsg> baseLocateMsgList;

    public BatLocateMsg() {
    }

    public BatLocateMsg(PackageData packageData) {
        this();
        this.channel = packageData.getChannel();
        this.checkSum = packageData.getCheckSum();
        this.msgBodyBytes = packageData.getMsgBodyBytes();
        this.msgHeader = packageData.getMsgHeader();
    }

    public int getDataItemTotal() {
        return dataItemTotal;
    }

    public void setDataItemTotal(int dataItemTotal) {
        this.dataItemTotal = dataItemTotal;
    }

    public int getLocateDataType() {
        return locateDataType;
    }

    public void setLocateDataType(int locateDataType) {
        this.locateDataType = locateDataType;
    }

    public int getLocateDateBodyLength() {
        return locateDateBodyLength;
    }

    public void setLocateDateBodyLength(int locateDateBodyLength) {
        this.locateDateBodyLength = locateDateBodyLength;
    }

    public List<LocationInfoUploadMsg> getBaseLocateMsgList() {
        return baseLocateMsgList;
    }

    public void setBaseLocateMsgList(List<LocationInfoUploadMsg> baseLocateMsgList) {
        this.baseLocateMsgList = baseLocateMsgList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BatLocateMsg that = (BatLocateMsg) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "BatLocateMsg{" +
                "dataItemTotal=" + dataItemTotal +
                ", locateDataType=" + locateDataType +
                ", locateDateBodyLength=" + locateDateBodyLength +
                ", baseLocateMsgList=" + baseLocateMsgList +
                '}';
    }
}
