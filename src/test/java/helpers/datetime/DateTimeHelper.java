package helpers.datetime;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateTimeHelper {

    /**
     * Convert Date to new Date format
     *
     * @param date   input date
     * @param format that want to get
     * @return new Date string
     */
    public static String convertDateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Get current Date
     *
     * @return new Date
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
