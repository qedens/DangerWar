package cc.zoyn.wastelandwarcore.util;

import cc.zoyn.wastelandwarcore.module.common.ui.UI;
import org.apache.commons.lang3.Validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public final class TimeUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";

    // 防止意外操作
    private TimeUtils() {
    }

    /**
     * 判断今日是否为休假日
     *
     * @return true [是] / false [不是]
     */
    public static boolean isWeekDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 日期格式化
     *
     * @param date             日期对象
     * @param simpleDataFormat 格式化工具对象
     * @return 格式化后的日期
     */
    public static String getFormattedDate(Date date, SimpleDateFormat simpleDataFormat) {
        return Validate.notNull(simpleDataFormat).format(date);
    }

    /**
     * 日期格式化，默认日期格式 yyyy-MM-dd
     *
     * @param date 日期对象
     * @return 格式化后的日期
     */
    public static String getDefaultFormatDate(Date date) {
        return getFormattedDate(date, new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    }

    /**
     * 日期格式化，默认日期格式 yyyy年MM月dd日
     *
     * @param date 日期对象
     * @return 格式化后的日期
     */
    public static String getChineseDateFormat(Date date) {
        return getFormattedDate(date, new SimpleDateFormat(CHINESE_DATE_FORMAT));
    }

}
