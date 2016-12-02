package util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import play.Logger;

/**
 *
 * @author pedro
 */
public abstract class DateUtil {

    public static final String FORMAT_DATE_PATTERN = "yyyyMMddHHmmss";
    public static final String FORMAT_TIME_PATTERN = "hh:mm a";
    public static final String FORMAT_DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm a";
    public static final String FORMAT_DATE_PATTERN_VIEW = "yyyy-MM-dd";

    public static Date parseDate(String dateString) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateString,
                    DateTimeFormatter.ofPattern(FORMAT_DATE_PATTERN));
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        } catch (Exception e) {
        }

        return null;
    }

    public static Date parseTime(String timeString) {
        try {
            LocalTime localTime = LocalTime.parse(timeString,
                    DateTimeFormatter.ofPattern(FORMAT_TIME_PATTERN));

            LocalDateTime localDateTime = localTime.atDate(LocalDate.now());
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        } catch (Exception e) {
            Logger.error("error", e);
        }

        return null;
    }

    public static String formatDate(Date date, String pattern) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
