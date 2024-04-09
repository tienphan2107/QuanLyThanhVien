/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.util.regex.Pattern;

/**
 *
 * @author DELL
 */
public class Validation {
    public static Boolean isEmpty(String input) {
        if (input == null) {
            return true;
        }
        return input.equals("");
    }

    public static Boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean isNumber(String num) {
        boolean result = true;
        if (num == null) return false;
        try {
            long k = Long.parseLong(num);
            if(k < 0) {
                result = false;
            }
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
    
    public static boolean checkPhone(String num) {
        // Kiểm tra xem chuỗi num có đúng định dạng số điện thoại Việt Nam hay không
    // Mẫu số điện thoại Việt Nam có thể được biểu diễn bằng một biểu thức chính quy
    // Ví dụ: 0987654321, +84987654321, 84-987654321, 0084987654321
    // Bạn có thể điều chỉnh biểu thức chính quy tùy theo yêu cầu cụ thể

    // Loại bỏ các ký tự không phải là chữ số trong chuỗi số điện thoại
    String digitsOnly = num.replaceAll("[^0-9]", "");

    // Kiểm tra xem chuỗi số điện thoại đã chỉ chứa chữ số hay chưa
    if (!digitsOnly.equals(num)) {
        return false;
    }

    // Kiểm tra độ dài của chuỗi số điện thoại
    if (digitsOnly.length() != 10) {
        return false;
    }

    // Kiểm tra xem chuỗi số điện thoại có bắt đầu bằng 0 hoặc +84 không
    if (!digitsOnly.startsWith("0")) {
        return false;
    }

    // Kiểm tra xem chuỗi số điện thoại có đúng định dạng hay không
    // Sử dụng biểu thức chính quy để so khớp với mẫu số điện thoại
    // Ví dụ: ^0\d{9}$ hoặc ^84\d{9}$
    String regex = "^0\\d{9}$";
    return digitsOnly.matches(regex);
    }
}
