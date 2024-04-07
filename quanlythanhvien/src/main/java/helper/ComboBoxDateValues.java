package helper;

import BLL.ThongTinSuDungBLL;
import GUI.Dialog.SelectDateDialog;
import POJO.DateRange;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComboBoxDateValues {

    private static final LocalDateTime defaultMinDate = LocalDateTime.of(2019, 1, 1, 0, 0);
    private static final LocalDateTime minDate;

    static {
        LocalDateTime tempMinDate = DateHelper.convertDateObjToLDT(new ThongTinSuDungBLL().getMinDate());
        minDate = tempMinDate == null ? defaultMinDate : tempMinDate;
    }
    private static final LocalDateTime nowDateTime = LocalDateTime.now();
    private static final LocalDateTime lastMonthDateTime = nowDateTime.minusMonths(1);
    private static final LocalDateTime last2MonthDateTime = nowDateTime.minusMonths(2);
    private static final int CUR_YEAR = nowDateTime.getYear();
    private static final int CUR_MONTH = nowDateTime.getMonthValue();

    private static final String LAST_7_DAYS = "7 ngày qua";
    private static final String LAST_30_DAYS = "30 ngày qua";
    private static final String LAST_90_DAYS = "90 ngày qua";
    private static final String LAST_365_DAYS = "365 ngày qua";
    private static final String LIFETIME = "Toàn thời gian";
    private static final String CURRENT_YEAR = Integer.toString(CUR_YEAR);
    private static final String LAST_YEAR = Integer.toString(CUR_YEAR - 1);
    private static final String CURRENT_MONTH = "Tháng " + CUR_MONTH;
    private static final String LAST_MONTH = "Tháng " + lastMonthDateTime.getMonthValue() + (lastMonthDateTime.getYear() == CUR_YEAR ? "" : "/" + lastMonthDateTime.getYear());
    private static final String LAST_2_MONTHS = "Tháng " + last2MonthDateTime.getMonthValue() + (last2MonthDateTime.getYear() == CUR_YEAR ? "" : "/" + last2MonthDateTime.getYear());
    private static final String CUSTOM = "Tuỳ chỉnh";

    public static final String GROUP_BY_DATE = "Ngày";
    public static final String GROUP_BY_MONTH = "Tháng";
    public static final String GROUP_BY_YEAR = "Năm";
    public static final int QUERY_MAX_DATES = 90;

    public static String[] getComboBoxDateValues() {
        return new String[]{LAST_7_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_365_DAYS,
            LIFETIME, CURRENT_YEAR, LAST_YEAR, CURRENT_MONTH, LAST_MONTH,
            LAST_2_MONTHS, CUSTOM};
    }

    public static String[] getComboBoxGroupByValues() {
        return new String[]{GROUP_BY_DATE, GROUP_BY_MONTH, GROUP_BY_YEAR};
    }

    public static boolean handleComboBoxChanged(JPanel panel, String value, DateRange dateRange, SelectDateCallback callback) {
        if (value.equals(CUSTOM)) {
            SelectDateDialog selectDate = new SelectDateDialog((JFrame) javax.swing.SwingUtilities.getWindowAncestor(panel), true, dateRange, 0, false, defaultMinDate, callback);
            selectDate.setVisible(true);
            return false;
        }
        if (value.equals(LAST_7_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(6));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(LAST_30_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(29));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(LAST_90_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(89));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(LAST_365_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(364));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(LIFETIME)) {
            dateRange.setFromDate(minDate);
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CURRENT_YEAR)) {
            dateRange.setFromDate(LocalDateTime.of(CUR_YEAR, 1, 1, 0, 0));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(LAST_YEAR)) {
            LocalDateTime firstDayOfCurYear = LocalDateTime.of(CUR_YEAR, 1, 1, 0, 0, 0);
            LocalDateTime lastDayOfLastYear = firstDayOfCurYear.minusDays(1);
            dateRange.setFromDate(LocalDateTime.of(CUR_YEAR - 1, 1, 1, 0, 0));
            dateRange.setToDate(lastDayOfLastYear);
        } else if (value.equals(CURRENT_MONTH)) {
            dateRange.setFromDate(LocalDateTime.of(CUR_YEAR, nowDateTime.getMonthValue(), 1, 0, 0));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(LAST_MONTH)) {
            LocalDateTime firstDayOfCurMonth = LocalDateTime.of(CUR_YEAR, nowDateTime.getMonthValue(), 1, 0, 0);
            LocalDateTime lastDayOfLastMonth = firstDayOfCurMonth.minusDays(1);
            dateRange.setFromDate(LocalDateTime.of(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthValue(), 1, 0, 0));
            dateRange.setToDate(lastDayOfLastMonth);
        } else if (value.equals(LAST_2_MONTHS)) {
            LocalDateTime firstDayOfLastMonth = LocalDateTime.of(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthValue(), 1, 0, 0);
            LocalDateTime lastDayOfLast2Months = firstDayOfLastMonth.minusDays(1);
            dateRange.setFromDate(LocalDateTime.of(last2MonthDateTime.getYear(), last2MonthDateTime.getMonthValue(), 1, 0, 0));
            dateRange.setToDate(lastDayOfLast2Months);
        }
        return true;
    }
}
