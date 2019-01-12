package com.tuocheng.jt808.common;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * =================================
 *
 * @ClassName MyClassificationLogAppender
 * @Description TODO
 * @Author nongfeng
 * @Date 19-1-12 下午3:42
 * @Version v1.0.0
 * ==================================
 */
public class MyClassificationLogAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.getThreshold().equals(priority);
    }
}
