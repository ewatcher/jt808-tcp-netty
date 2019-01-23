package com.tuocheng.jt808.vo;

/**
 * =================================
 *
 * @ClassName CameraControlByTime
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-23 上午12:53
 * @Version v1.0.0
 * ==================================
 */
public class CameraControlByTime {

    /**
     * bit[0] 摄像通道 1 定时拍照开关标志 0:不允许; 1:允许
     */
    private boolean chanel1CameraFlag;

    /**
     * bit[1] 摄像通道 2 定时拍照开关标志 0:不允许; 1:允许
     */
    private boolean chanel2CameraFlag;

    /**
     * bit[2] 摄像通道 3 定时拍照开关标志 0:不允许; 1:允许
     */
    private boolean chanel3CameraFlag;

    /**
     * bit[3] 摄像通道 4 定时拍照开关标志 0:不允许; 1:允许
     */
    private boolean chanel4CameraFlag;

    /**
     * bit[4] 摄像通道 5 定时拍照开关标志 0:不允许; 1:允许
     */
    private boolean chanel5CameraFlag;

    /**
     * bit[5-7] 保留
     */
    private int holdBit57;

    /**
     * bit[8]  摄像通道 1 定时拍照存储标志  0:存储; 1:上传
     */
    private boolean chanel1CameraStoreFlag;

    /**
     * bit[9]  摄像通道 2 定时拍照存储标志  0:存储; 1:上传
     */
    private boolean chanel2CameraStoreFlag;

    /**
     * bit[10]  摄像通道 3 定时拍照存储标志  0:存储; 1:上传
     */
    private boolean chanel3CameraStoreFlag;

    /**
     * bit[11]  摄像通道 4 定时拍照存储标志  0:存储; 1:上传
     */
    private boolean chanel4CameraStoreFlag;

    /**
     * bit[12]  摄像通道 5 定时拍照存储标志  0:存储; 1:上传
     */
    private boolean chanel5CameraStoreFlag;

    /**
     * bit[13-15] 保留
     */
    private int holdBit1315;

    /**
     * bit[16]  定时时间单位 0:秒,当数值小于 5 秒时,终端按 5 秒处理;1:分
     */
    private boolean regularTimeUnit;

    /**
     * bit[17-31]  定时时间间隔 收到参数设置或重新启动后执行
     */
    private int regularTimeBeat;

    public boolean isChanel1CameraFlag() {
        return chanel1CameraFlag;
    }

    public void setChanel1CameraFlag(boolean chanel1CameraFlag) {
        this.chanel1CameraFlag = chanel1CameraFlag;
    }

    public boolean isChanel2CameraFlag() {
        return chanel2CameraFlag;
    }

    public void setChanel2CameraFlag(boolean chanel2CameraFlag) {
        this.chanel2CameraFlag = chanel2CameraFlag;
    }

    public boolean isChanel3CameraFlag() {
        return chanel3CameraFlag;
    }

    public void setChanel3CameraFlag(boolean chanel3CameraFlag) {
        this.chanel3CameraFlag = chanel3CameraFlag;
    }

    public boolean isChanel4CameraFlag() {
        return chanel4CameraFlag;
    }

    public void setChanel4CameraFlag(boolean chanel4CameraFlag) {
        this.chanel4CameraFlag = chanel4CameraFlag;
    }

    public boolean isChanel5CameraFlag() {
        return chanel5CameraFlag;
    }

    public void setChanel5CameraFlag(boolean chanel5CameraFlag) {
        this.chanel5CameraFlag = chanel5CameraFlag;
    }

    public int getHoldBit57() {
        return holdBit57;
    }

    public void setHoldBit57(int holdBit57) {
        this.holdBit57 = holdBit57;
    }

    public boolean isChanel1CameraStoreFlag() {
        return chanel1CameraStoreFlag;
    }

    public void setChanel1CameraStoreFlag(boolean chanel1CameraStoreFlag) {
        this.chanel1CameraStoreFlag = chanel1CameraStoreFlag;
    }

    public boolean isChanel2CameraStoreFlag() {
        return chanel2CameraStoreFlag;
    }

    public void setChanel2CameraStoreFlag(boolean chanel2CameraStoreFlag) {
        this.chanel2CameraStoreFlag = chanel2CameraStoreFlag;
    }

    public boolean isChanel3CameraStoreFlag() {
        return chanel3CameraStoreFlag;
    }

    public void setChanel3CameraStoreFlag(boolean chanel3CameraStoreFlag) {
        this.chanel3CameraStoreFlag = chanel3CameraStoreFlag;
    }

    public boolean isChanel4CameraStoreFlag() {
        return chanel4CameraStoreFlag;
    }

    public void setChanel4CameraStoreFlag(boolean chanel4CameraStoreFlag) {
        this.chanel4CameraStoreFlag = chanel4CameraStoreFlag;
    }

    public boolean isChanel5CameraStoreFlag() {
        return chanel5CameraStoreFlag;
    }

    public void setChanel5CameraStoreFlag(boolean chanel5CameraStoreFlag) {
        this.chanel5CameraStoreFlag = chanel5CameraStoreFlag;
    }

    public int getHoldBit1315() {
        return holdBit1315;
    }

    public void setHoldBit1315(int holdBit1315) {
        this.holdBit1315 = holdBit1315;
    }

    public boolean isRegularTimeUnit() {
        return regularTimeUnit;
    }

    public void setRegularTimeUnit(boolean regularTimeUnit) {
        this.regularTimeUnit = regularTimeUnit;
    }

    public int getRegularTimeBeat() {
        return regularTimeBeat;
    }

    public void setRegularTimeBeat(int regularTimeBeat) {
        this.regularTimeBeat = regularTimeBeat;
    }

    @Override
    public String toString() {
        return "CameraControlByTime{" +
                "chanel1CameraFlag=" + chanel1CameraFlag +
                ", chanel2CameraFlag=" + chanel2CameraFlag +
                ", chanel3CameraFlag=" + chanel3CameraFlag +
                ", chanel4CameraFlag=" + chanel4CameraFlag +
                ", chanel5CameraFlag=" + chanel5CameraFlag +
                ", holdBit57=" + holdBit57 +
                ", chanel1CameraStoreFlag=" + chanel1CameraStoreFlag +
                ", chanel2CameraStoreFlag=" + chanel2CameraStoreFlag +
                ", chanel3CameraStoreFlag=" + chanel3CameraStoreFlag +
                ", chanel4CameraStoreFlag=" + chanel4CameraStoreFlag +
                ", chanel5CameraStoreFlag=" + chanel5CameraStoreFlag +
                ", holdBit1315=" + holdBit1315 +
                ", regularTimeUnit=" + regularTimeUnit +
                ", regularTimeBeat=" + regularTimeBeat +
                '}';
    }
}
