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
    
    public int getMaXuLyAutoIncreasement(){
        return xuLyDAL.loadXuLy().size() + 1;
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
}
