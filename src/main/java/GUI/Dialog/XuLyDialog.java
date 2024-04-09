/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BLL.ThanhVienBLL;
import BLL.XuLyBLL;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.ThanhVienPanel;
import helper.Validation;
import hibernatemember.DAL.ThanhVien;
import hibernatemember.DAL.ThanhVienDAL;
import hibernatemember.DAL.XuLy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

/**
 *
 * @author DELL
 */
public class XuLyDialog extends JDialog {

    private ThanhVienBLL thanhVienBLL = new ThanhVienBLL();
    private XuLyBLL xuLyBLL = new XuLyBLL();
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm txtMaTV, txtTenTV, txtSoTien;
    SelectForm cbbHinhThucXL, cbbTrangthaiXL;
    private InputDate ipDate;
    private XuLy xuLy;
    private ThanhVien thanhVien;

    public XuLyDialog(JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public XuLyDialog(JFrame owner, boolean modal, String title, String type, XuLy xuLy) {
        super(owner, title, modal);
        this.xuLy = xuLy;
        this.thanhVien = thanhVienBLL.getThanhVien(xuLy.getThanhVien().getMaTV());
//        this.tvPanel = this.tvPanel;
        init(title, type);
        txtMaTV.setText(xuLy.getThanhVien().getMaTV() + "");
        ipDate.setDate(xuLy.getNgayXL());
        txtTenTV.setText(thanhVien.getHoTen());
        cbbTrangthaiXL.setValue(xuLy.getTrangThaiXL() == 0 ? "Đã xử lý" : "Đang xử lý");
        cbbHinhThucXL.setValue(xuLy.getHinhThucXL());
        txtSoTien.setText(xuLy.getSoTien() + "");
        
        if(!xuLy.getHinhThucXL().contains("ồi thường")){
            txtSoTien.setDisable();
        }

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public interface ThanhVienPanelCallback {

        void loadDataTableCallback();
    }

    public void init(String title, String type) {
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);
        txtMaTV = new InputForm("Mã thành viên");
        txtTenTV = new InputForm("Tên thành viên");
        txtSoTien = new InputForm("Số tiền");
        txtSoTien.setText("0");

        ArrayList<String> ListHinhThucXL = xuLyBLL.getHinhThucXuLy();
        String[] arrHinhThucXL = ListHinhThucXL.toArray(new String[ListHinhThucXL.size()]);
        cbbHinhThucXL = new SelectForm("Hình thức xử lý", arrHinhThucXL);

        String[] arrTrangThaiXuLy = {"Đã xử lý", "Đang xử lý"};
        cbbTrangthaiXL = new SelectForm("Trạng thái xử lý", arrTrangThaiXuLy);

        JPanel jpanelG = new JPanel(new GridLayout(2, 1, 0, 2));
        jpanelG.setBackground(Color.white);
        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lbBd = new JLabel("Ngày xử lý");
        lbBd.setSize(new Dimension(100, 100));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);

        ipDate = new InputDate("Ngày Xử Lý");
        ipDate.setSize(new Dimension(100, 100));
        Date currentDate = new Date(System.currentTimeMillis());
        ipDate.setDate(currentDate);
        jpaneljd.add(lbBd);
        jpaneljd.add(ipDate);

        main.add(txtMaTV);
        main.add(txtTenTV);
        main.add(cbbHinhThucXL);
        main.add(txtSoTien);
        main.add(ipDate);
        main.add(cbbTrangthaiXL);

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm", "success", 14);
        btnEdit = new ButtonCustom("Lưu thông tin", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maXuLy = xuLyBLL.getMaXuLyAutoIncreasement();
                    String strMaThanhVien = txtMaTV.getText().trim();
                    String strSoTien = txtSoTien.getText().trim();
                    Date ngay = ipDate.getDate();
                    String hinhThucXuLy = cbbHinhThucXL.getValue();
                    int trangThaiXuLy = cbbTrangthaiXL.getSelectedIndex(); // da xu ly
                    String checkMessage;
                    if (hinhThucXuLy.contains("ồi thường")) {
                        checkMessage = xuLyBLL.CheckValue(strMaThanhVien, strSoTien);
                    } else {
                        checkMessage = xuLyBLL.CheckValue(strMaThanhVien, "1000");
                    }
                    if (!checkMessage.isEmpty()) {
                        JOptionPane.showConfirmDialog(rootPane, checkMessage);
                        return;
                    }

                    int maThanhVien = Integer.parseInt(strMaThanhVien);
                    int soTien = Integer.parseInt(strSoTien);

                    XuLy xuLy = new XuLy(maXuLy, maThanhVien, hinhThucXuLy, soTien, new java.sql.Date(ngay.getTime()), trangThaiXuLy);

                    if (xuLyBLL.AddXuLy(xuLy)) {
                        JOptionPane.showConfirmDialog(rootPane, "Thành công !");
                    } else {
                        JOptionPane.showConfirmDialog(rootPane, "Thất bại !");
                    }
                    dispose();
                } catch (ParseException ex) {
                    Logger.getLogger(XuLyDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cbbHinhThucXL.getCbb().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (!cbbHinhThucXL.getValue().contains("ồi thường")) {
                        txtSoTien.setDisable();
                    } else {
                        txtSoTien.setEnable();
                        txtSoTien.setText("0");
                    }
                }
            }
        });

        txtMaTV.getTxtForm().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                return;
            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    int maThanhVien = Integer.parseInt(txtMaTV.getText().trim());
                    ThanhVien thanhVien = thanhVienBLL.getThanhVien(maThanhVien);
                    if (thanhVien != null) {
                        txtTenTV.setText(thanhVien.getHoTen());
                    } else {
                        txtTenTV.setText("Không tìm thấy thành viên có mã này !");
                    }
                } catch (Exception ex) {
                    txtTenTV.setText("Không tìm thấy thành viên có mã này !");
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maXuLy = xuLy.getMaXL();
                    String strMaThanhVien = txtMaTV.getText().trim();
                    String strSoTien = txtSoTien.getText().trim();
                    Date ngay = ipDate.getDate();
                    String hinhThucXuLy = cbbHinhThucXL.getValue();
                    int trangThaiXuLy = cbbTrangthaiXL.getSelectedIndex(); // dang xu ly
                    String checkMessage = xuLyBLL.CheckValue(strMaThanhVien, strSoTien);
                    if (!checkMessage.isEmpty()) {
                        JOptionPane.showConfirmDialog(rootPane, checkMessage);
                        return;
                    }

                    int maThanhVien = Integer.parseInt(strMaThanhVien);
                    int soTien = Integer.parseInt(strSoTien);

                    XuLy xuLy = new XuLy(maXuLy, maThanhVien, hinhThucXuLy, soTien, new java.sql.Date(ngay.getTime()), trangThaiXuLy);

                    if (xuLyBLL.UpdateXuLy(xuLy)) {
                        JOptionPane.showConfirmDialog(rootPane, "Thành công !");
                    } else {
                        JOptionPane.showConfirmDialog(rootPane, "Thất bại !");
                    }
                    dispose();
                } catch (ParseException ex) {
                    Logger.getLogger(XuLyDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        switch (type) {
            case "create" -> {
                txtTenTV.setDisable();
                ipDate.setDisable();
                bottom.add(btnAdd);
            }
            case "update" -> {
                bottom.add(btnEdit);
                txtTenTV.setDisable();
                ipDate.setDisable();
            }
            default ->
                throw new AssertionError();
        }
        bottom
                .add(btnExit);

        this.add(titlePage, BorderLayout.NORTH);

        this.add(main, BorderLayout.CENTER);

        this.add(bottom, BorderLayout.SOUTH);
        cbbHinhThucXL.actionPerformed(null);

    }
}
