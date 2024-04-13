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
    public boolean checkExist(int id){
        ArrayList<ThanhVien> list = thanhvienDAL.loadThanhVien();
        for(ThanhVien tv : list){
            if(tv.getMaTV() == id) return true;
        }
        return false;
    }

    public boolean newThanhVien(ThanhVien c) {
        return thanhvienDAL.addThanhVien(c);
    }

    public boolean updateThanhVien(ThanhVien c) {
        return thanhvienDAL.updateThanhVien(c);
    }

    public boolean deleteThanhVien(ThanhVien c) {
        return thanhvienDAL.deleteThanhVien(c);
    }

    public ThanhVien getThanhVien(int thanhvienID) {
        ThanhVien c = thanhvienDAL.getThanhVien(thanhvienID);
        return c;
    }
    public int getAutoIncrement() {
        return thanhvienDAL.getAutoIncrement();
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


}
