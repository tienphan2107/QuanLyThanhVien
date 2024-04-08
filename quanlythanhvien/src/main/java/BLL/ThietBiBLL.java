/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import GUI.Dialog.ThietBiDialog;
import hibernatemember.DAL.ThietBi;
import hibernatemember.DAL.ThietBiDAL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class ThietBiBLL implements ActionListener {

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

    public int getMa() {
        return tb.getMa();
    }

    public String getTen() {
        return tb.getTen();
    }

    public String getMota() {
        return tb.getMota();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn = e.getActionCommand();
        switch (btn) {
            case "THÊM" -> {
                ThietBiDialog tbthem = new ThietBiDialog(this, tb.owner, true, "Thêm thiết bị", "create");
            }
            case "XÓA" -> {
                ThietBiDialog tbthem = new ThietBiDialog(this, tb.owner, true, "Xóa thiết bị", "delete");
            }
            case "SỬA" -> {
                if (tb.getRow() == -1) {
                    JOptionPane.showMessageDialog(tb, "Vui lòng chọn 1 Thiết bị", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                } else {
                    ThietBiDialog tbthem = new ThietBiDialog(this, tb.owner, true, "Sửa thiết bị", "update");
                }
            }
            case "CHI TIẾT" -> {
                if (tb.getRow() == -1) {
                    JOptionPane.showMessageDialog(tb, "Vui lòng chọn 1 Thiết bị", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                } else {
                    ThietBiDialog tbthem = new ThietBiDialog(this, tb.owner, true, "Xem chi tiết", "view");
                }
            }
            case "NHẬP EXCEL" -> {
                
            }
            case "XUẤT EXCEL" -> {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    tb.exportToExcel(new File(file.getPath() + ".xls"));
                }
            }
        }
        tb.loadDataTable();
    }
}
