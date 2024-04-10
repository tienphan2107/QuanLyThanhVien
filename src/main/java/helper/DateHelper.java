package helper;

import POJO.DateRange;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String MONTH_PATTERN = "MM/yyyy";
    public static final String YEAR_PATTERN = "yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter EXCEL_SHEET_NAME_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern(MONTH_PATTERN);
    public static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern(YEAR_PATTERN);
    public static final SimpleDateFormat SQL_ROW_MONTH_FORMATTER = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat SQL_ROW_YEAR_FORMATTER = new SimpleDateFormat("yyyy");
    public static final DateTimeFormatter SQL_ROW_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String SQL_QUERY_MONTH_FORMAT = "%Y-%m";
    public static final String SQL_QUERY_YEAR_FORMAT = "%Y";

    public static LocalDateTime convertDateObjToLDT(Date date) {
        if (date == null) {
            return null;
        }

        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate().atStartOfDay();
        } else {
            return date.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        }
    }

    public static Date convertLDTToDateObj(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String dateRangeToString(DateRange dateRange, DateTimeFormatter formatter, String separator) {
        return dateRange.getFromDate().format(formatter) + separator + dateRange.getToDate().format(formatter);
    }
}
