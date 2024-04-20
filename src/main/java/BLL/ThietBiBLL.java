/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import hibernatemember.DAL.ThietBi;
import hibernatemember.DAL.ThietBiDAL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author DELL
 */
public class ThietBiBLL {

    private GUI.Panel.ThietBiPanel tb;
    private ThietBiDAL thietbiDAL;

    public ThietBiBLL() {
        thietbiDAL = new ThietBiDAL();
    }

    public ThietBiBLL(GUI.Panel.ThietBiPanel tb) {
        this.tb = tb;
        thietbiDAL = new ThietBiDAL();
    }

    public List loadThietBi() {
        List list;
        list = thietbiDAL.loadThietBi();

        return list;
    }

    public void newThietBi(ThietBi c) {
        thietbiDAL.addThietBi(c);
    }

    public ThietBi getThietBi(int thietbiID) {
        ThietBi c = thietbiDAL.getThietBi(thietbiID);
        return c;
    }

    public ArrayList<String> getDanhSachLoaiThietBi() {
        return thietbiDAL.getDanhSachLoaiThietBi();
    }
    
    public String[] getListTenTB() {
        return thietbiDAL.getListTenTB();
    }
    
    public int getMaThietBi(String tenThietBi) {
        return thietbiDAL.getMaThietBi(tenThietBi);
    }
    
    public String getTenThietBi(int maThietBi) {
        return thietbiDAL.getTenThietBi(maThietBi);
    }
    public void addThietBi(ThietBi c){
        thietbiDAL.addThietBi(c);
    }
    public void updateThietBi(ThietBi c){
        thietbiDAL.updateThietBi(c);
    }
    public void deleteThietBi(int maTB){
        thietbiDAL.deleteThietBi(maTB);
    }
    public void deleteThietBiByStartingNumber(String startnumber){
        thietbiDAL.deleteThietBiByStartingNumber(startnumber);
    }
    public String[] getThietBiStrings(){
        return thietbiDAL.getThietBiStrings();
    }
    public String generateDeviceCode(String diviceName){
        return thietbiDAL.generateDeviceCode(diviceName);
    }
    public void importFromExcel(File excelFile){
        thietbiDAL.importFromExcel(excelFile);
    }
    public void exportToExcel(File file, JTable tableThietBi ){
        thietbiDAL.exportToExcel(file, tableThietBi);
    }
    public List<ThietBi> loadThietBiFilter(String keyword){
        return thietbiDAL.loadThietBiFilter(keyword);
    }
}
