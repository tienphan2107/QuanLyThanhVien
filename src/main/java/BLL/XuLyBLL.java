/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import POJO.DateRange;
import hibernatemember.DAL.XuLy;
import hibernatemember.DAL.XuLyDAL;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Acer
 */
public class XuLyBLL {

    XuLyDAL xuLyDAL;
    ThanhVienBLL thanhVienBLL = new ThanhVienBLL();

    public XuLyBLL() {
        xuLyDAL = new XuLyDAL();
    }

    public int getMaXuLyAutoIncreasement() {
        ArrayList<XuLy> listXuLy = LoadXuLy();
        int lastIndex = LoadXuLy().size() - 1;
        return listXuLy.get(lastIndex).getMaXL() + 1;
    }

    public ArrayList<String> getHinhThucXuLy() {
        return xuLyDAL.getHinhThucXuLy();
    }

    public ArrayList<XuLy> LoadXuLy() {
        return xuLyDAL.loadXuLy();
    }

    public XuLy GetXuLy(int xuLyID) {
        return xuLyDAL.getXuLy(xuLyID);
    }

    public boolean AddXuLy(XuLy xuLy) {
        return xuLyDAL.addXuLy(xuLy);
    }

    public boolean UpdateXuLy(XuLy xuLy) {
        return xuLyDAL.updateXuLy(xuLy);
    }

    public boolean DeleteXuLy(XuLy xuLy) {
        return xuLyDAL.deleteXuLy(xuLy);
    }

    public String CheckValue(String strMaThanhVien, String strSoTien) {
        int maThanhVien = 0;
        int soTien = 0;
        String result = "";
        java.util.Date currentDate = new java.util.Date(System.currentTimeMillis());

        try {
            maThanhVien = Integer.parseInt(strMaThanhVien);
            soTien = Integer.parseInt(strSoTien);
        } catch (Exception e) {
            return "Mã thành viên và số tiền là số ! Vui lòng nhập lại";
        }
        if (thanhVienBLL.getThanhVien(maThanhVien) == null) {
            result += "\nKhông tìm thấy thành viên";
        }
        if (soTien < 500 || soTien > 100000000) {
            result += "\n Số tiền không hợp lệ (phải từ 500 VND đến 100 000 000 VND (100 triệu))";
        }

        return result;
    }

    public ArrayList<XuLy> getStatXuLy(int state, DateRange dateRange, String memberName) {
        return xuLyDAL.getStatXuLy(state, dateRange, memberName);
    }

    public Long tongTienBoiThuong(ArrayList<XuLy> list) {
        Long result = 0l;
        for (XuLy i : list) {
            result += Long.parseLong(i.getSoTien().toString());
        }
        return result;
    }

    public void updateTrangThaiXuLy() { // hàm này quét qua tất cả các xử lý xem cái nào hết thời gian xử lý thì cập nhật lại
        Date today = new Date(System.currentTimeMillis());
        ArrayList<XuLy> listXuLy = LoadXuLy();
        for (XuLy xuLy : listXuLy) {
            if (xuLy.getHinhThucXL().contains("thẻ")
                    && (xuLy.getHinhThucXL().contains("1") || xuLy.getHinhThucXL().contains("2") || xuLy.getHinhThucXL().contains("3"))) {
                // Tính toán ngày kết thúc của thời gian khóa thẻ
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(xuLy.getNgayXL());
                int soThangKhoaThe = 1;
                if (xuLy.getHinhThucXL().contains("2")) {
                    soThangKhoaThe = 2;
                } else if (xuLy.getHinhThucXL().contains("3")) {
                    soThangKhoaThe = 3;
                }
                calendar.add(Calendar.MONTH, soThangKhoaThe);
                Date ngayKetThuc = calendar.getTime();
                // Kiểm tra xem ngày hiện tại có vượt quá ngày kết thúc của thời gian khóa thẻ không
                if (today.after(ngayKetThuc)) {
                    xuLy.setTrangThaiXL(0);
                    xuLyDAL.updateXuLy(xuLy);
                }
            }
        }
    }
    
    public boolean ThanhVienViPham(int maTV){
        return xuLyDAL.ThanhVienViPham(maTV);
    }

}
