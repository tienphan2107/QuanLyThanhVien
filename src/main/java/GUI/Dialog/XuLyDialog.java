/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BLL.ThanhVienBLL;
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
import java.text.ParseException;
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

    private ThanhVienBLL tvBLL = new ThanhVienBLL();
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm MaTV, tenTV, soTien;
    SelectForm hinhthucXL, trangthaiXL;
    private InputDate ipDate;

    String[] arrHinhThucXL = {"Khóa thẻ 1 tháng", "Khóa thẻ 2 tháng", "Bồi thường mất tài sản", "Bồi thường hỏng tài sản"};

    public XuLyDialog(JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

//    public XuLyDialog(JFrame owner, boolean modal, String title, String type, ThanhVien thanhVien) {
//        super(owner, title, modal);
//        this.thanhVien = thanhVien;
////        this.tvPanel = this.tvPanel;
//        init(title, type);
//        MaTV.setText(thanhVien.getHoTen());
//        tenTV.setText(String.valueOf(thanhVien.getSDT()));
//        khoa.setValue(thanhVien.getKhoa());
//        nganh.setValue(thanhVien.getNganh());
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
//    }
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
        MaTV = new InputForm("Mã thành viên");
        tenTV = new InputForm("Tên thành viên");
        soTien = new InputForm("Số tiền");

//        arrHinhThucXL = tvBLL.getArrTenKhoa();
        hinhthucXL = new SelectForm("Hình thức xử lý", arrHinhThucXL);

//        sdt = new InputForm("Số điện thoại");
//        PlainDocument phonex = (PlainDocument) sdt.getTxtForm().getDocument();
//        phonex.setDocumentFilter((new NumericDocumentFilter()));
//        email = new InputForm("Email");
//        male = new JRadioButton("Nam");
//        female = new JRadioButton("Nữ");
//        gender = new ButtonGroup();
//        gender.add(male);
//        gender.add(female);
        JPanel jpanelG = new JPanel(new GridLayout(2, 1, 0, 2));
        jpanelG.setBackground(Color.white);
        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));
//        JPanel jgender = new JPanel(new GridLayout(1, 2));
//        jgender.setSize(new Dimension(500, 80));
//        jgender.setBackground(Color.white);
//        jgender.add(male);
//        jgender.add(female);
//        JLabel labelGender = new JLabel("Giới tính");
//        jpanelG.add(labelGender);
//        jpanelG.add(jgender);
        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lbBd = new JLabel("Ngày xử lý");
        lbBd.setSize(new Dimension(100, 100));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);
        ipDate = new InputDate("Ngày Xử Lý");
        ipDate.setSize(new Dimension(100, 100));
        jpaneljd.add(lbBd);
        jpaneljd.add(ipDate);
        main.add(MaTV);
        main.add(tenTV);
        main.add(hinhthucXL);
        main.add(soTien);
        main.add(ipDate);

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm thành viên", "success", 14);
        btnEdit = new ButtonCustom("Lưu thông tin", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

//        btnAdd.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (ValidationInput()) {
//
//                        int manv = tvBLL.getAutoIncrement();
//                        String txtName = name.getText();
//                        int txtSdt = Integer.parseInt(sdt.getText());
//                        String txtKhoa = (String) khoa.getSelectedItem();
//                        String txtNganh = (String) nganh.getSelectedItem();
//                        XuLy xL = new XuLy(manv, txtName, txtKhoa, txtNganh, txtSdt);
//                        tvBLL.newThanhVien(xL);
//                        dispose();
//                    }
//                } catch (ParseException ex) {
//                    Logger.getLogger(XuLyDialog.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });

//        btnEdit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (ValidationInput()) {
//                        int manv = tvBLL.getAutoIncrement();
//                        String txtName = name.getText();
//                        int txtSdt = Integer.parseInt(sdt.getText());
//                        String txtKhoa = (String) khoa.getSelectedItem();
//                        String txtNganh = (String) nganh.getSelectedItem();
//                        ThanhVien tV = new ThanhVien(thanhVien.getMaTV(), txtName, txtKhoa, txtNganh, txtSdt);
//                        tvBLL.updateThanhVien(tV);
//                        dispose();
//                    }
//                } catch (ParseException ex) {
//                    Logger.getLogger(XuLyDialog.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
        switch (type) {
            case "create" -> {
                tenTV.setDisable();
                ipDate.setDisable();
                bottom.add(btnAdd);
            }    
            case "update" ->
                bottom.add(btnEdit);
            case "detail" -> {
//                name.setDisable();
//                sdt.setDisable();
//                khoa.setDisable();
//                nganh.setDisable();
//                email.setDisable();
//                Enumeration<AbstractButton> enumeration = gender.getElements();
//                while (enumeration.hasMoreElements()) {
//                    enumeration.nextElement().setEnabled(false);
//                }
//                jcBd.setDisable();
            }
            default ->
                throw new AssertionError();
        }
        bottom
                .add(btnExit);

        this.add(titlePage, BorderLayout.NORTH);

        this.add(main, BorderLayout.CENTER);

        this.add(bottom, BorderLayout.SOUTH);

    }

//    boolean ValidationInput() throws ParseException {
//        if (Validation.isEmpty(name.getText())) {
//            JOptionPane.showMessageDialog(this, "Tên thành viên không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
//            return false;
//        } else if (name.getText().length() < 6) {
//            JOptionPane.showMessageDialog(this, "Tên thành viên ít nhất 6 kí tự!");
//            return false;
//        } //        else if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
//        //            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
//        //            return false;
//        //        }
//        else if (Validation.isEmpty(sdt.getText()) && !Validation.isNumber(sdt.getText()) && sdt.getText().length() != 10) {
//            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
//            return false;
//        }
////        else if(jcBd.getDate()==null){
////            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
////            return false;
////        } else if(!male.isSelected() && !female.isSelected()){
////            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
////            return false;
////        }
//
//        return true;
//    }
}
