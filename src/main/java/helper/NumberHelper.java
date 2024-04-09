package helper;

import java.text.NumberFormat;

public class NumberHelper {

    public static String commafy(Long number) {
        return NumberFormat.getNumberInstance().format(number);
    }

    public static String appendVND(String str) {
        return str + " đ";
    }
}
