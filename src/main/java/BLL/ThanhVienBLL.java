/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import hibernatemember.DAL.ThanhVien;
import hibernatemember.DAL.ThanhVienDAL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ThanhVienBLL{

    private ThanhVienDAL thanhvienDAL;

    public ThanhVienBLL() {
        thanhvienDAL = new ThanhVienDAL();
    }

    public ArrayList loadThanhVien() {
        ArrayList list;
        list = thanhvienDAL.loadThanhVien();

        return list;
    }

    public String[] getArrTenKhoa() {
        ArrayList<ThanhVien> listKhoa = loadThanhVien();
        String[] result = new String[listKhoa.size()];
        for (int i = 0; i < listKhoa.size(); i++) {
            result[i] = listKhoa.get(i).getKhoa();
        }
        return result;
    }

    public String[] getArrTenNganh() {
        ArrayList<ThanhVien> listNganh = new ArrayList<>(loadThanhVien());
        String[] result = new String[listNganh.size()];
        for (int i = 0; i < listNganh.size(); i++) {
            result[i] = listNganh.get(i).getNganh();
        }
        return result;
    }

    public ThanhVien[] convertList1(List<ThanhVien> list) {
        int rows = list.size();
        ThanhVien[] newList = new ThanhVien[rows];
        for (int i = 0; i < rows; i++) {
            newList[i] = list.get(i);

        }
        return newList;
    }

    public void newThanhVien(ThanhVien c) {
        thanhvienDAL.addThanhVien(c);
    }

    public void updateThanhVien(ThanhVien c) {
        thanhvienDAL.updateThanhVien(c);
    }

    public void deleteThanhVien(ThanhVien c) {
        thanhvienDAL.deleteThanhVien(c);
    }

    public ThanhVien getThanhVien(int thanhvienID) {
        ThanhVien c = thanhvienDAL.getThanhVien(thanhvienID);
        return c;
    }

    public String createMaTV() {
        return thanhvienDAL.createMaTV();
    }
    
    public ArrayList<String> getListKhoa(String query) {
        return thanhvienDAL.getListKhoa(query);
    }
    
    public ArrayList<String> getListNganh(String query) {
        return thanhvienDAL.getListNganh(query);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String btn = e.getActionCommand();
//        switch (btn) {
//            case "THÊM" -> {
//                ThanhVienDialog tvthem = new ThanhVienDialog(this, tv.owner, true, "Thêm thành viên", "create");
//            }
//            case "SỬA" -> {
//                int index = tv.getRow();
//                if (index < 0) {
//                    JOptionPane.showMessageDialog(null, "Vui lòng chọn thành viên cần sửa");
//                } else {
//                    ThanhVienDialog tvsua = new ThanhVienDialog(this, tv.owner, true, "Sửa thành viên", "update", tv.getNhanVien());
//                }
//            }
//            case "XÓA" -> {
//                if (tv.getRow() < 0) {
//                    JOptionPane.showMessageDialog(null, "Vui lòng chọn thành viên cần xóa");
//                } else {
//                    deleteThanhVien(tv.getNhanVien());
//                }
//            }
//        }
//        tv.loadDataTable();
//    }
}
