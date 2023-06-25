package helpers.datetime;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class DateTimeGenerator {

    /**
     * Generate DateTime On Current Date
     *
     * @param dateTimeFormat that want to get
     * @return Date in string
     */
    public static String generateCurrentDate(String dateTimeFormat) {
        Date generateDate = DateTimeHelper.getCurrentDate();
        return DateTimeHelper.convertDateFormat(generateDate, dateTimeFormat);
    }

}
