/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BLL.ThanhVienBLL;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import GUI.Panel.ThanhVienPanel;
import helper.Validation;
import hibernatemember.DAL.ThanhVien;
import hibernatemember.DAL.ThanhVienDAL;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

/**
 *
 * @author DELL
 */
public class ThanhVienDialog extends JDialog {
    
    private ThanhVienBLL tvBLL = new ThanhVienBLL();
    private ThanhVienPanel tvPanel;
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm name;
    SelectForm khoa, nganh;
    private InputForm sdt;
    private ThanhVien thanhVien;
    
    String[] arrKhoa;
    String[] arrNganh;
    
    public ThanhVienDialog(JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

//    public ThanhVienDialog(ThanhVienBLL tv, JFrame owner, boolean modal, String title, String type) {
//        super(owner, title, modal);
//        this.tv = tv;
//        init(title, type);
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
//    }
    public ThanhVienDialog(JFrame owner, boolean modal, String title, String type, ThanhVien thanhVien) {
        super(owner, title, modal);
        this.thanhVien = thanhVien;
//        this.tvPanel = this.tvPanel;
        init(title, type);
        name.setText(thanhVien.getHoTen());
        sdt.setText(String.valueOf(thanhVien.getSDT()));
        khoa.setValue(thanhVien.getKhoa());
        nganh.setValue(thanhVien.getNganh());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void init(String title, String type) {
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));
        
        titlePage = new HeaderTitle(title.toUpperCase());
        
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);
        name = new InputForm("Họ và tên");
        arrKhoa = tvBLL.getArrTenKhoa();
        khoa = new SelectForm("Khoa", arrKhoa);
        arrNganh = tvBLL.getArrTenNganh();
        nganh = new SelectForm("Ngành", arrNganh);
        
        sdt = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) sdt.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
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
//        JPanel jpaneljd = new JPanel();
//        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
//        JLabel lbBd = new JLabel("Ngày sinh");
//        lbBd.setSize(new Dimension(100, 100));
//        jpaneljd.setSize(new Dimension(500, 100));
//        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
//        jpaneljd.setBackground(Color.white);
//        jcBd = new InputDate("Ngày sinh");
//        jcBd.setSize(new Dimension(100, 100));
//        jpaneljd.add(lbBd);
//        jpaneljd.add(jcBd);
        main.add(name);
        main.add(khoa);
        main.add(nganh);
        main.add(sdt);
        main.add(jpanelG);
//        main.add(jcBd);

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
        
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ValidationInput()) {
                        
                        int manv =Integer.parseInt(tvBLL.createMaTV());
                        String txtName = name.getText();
                        int txtSdt = Integer.parseInt(sdt.getText());
                        String txtKhoa = (String) khoa.getSelectedItem();
                        String txtNganh = (String) nganh.getSelectedItem();
                        ThanhVien tV = new ThanhVien(manv, txtName, txtKhoa, txtNganh, txtSdt);
                        tvBLL.newThanhVien(tV);
                        dispose();
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ThanhVienDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ValidationInput()) {
                        String txtName = name.getText();
                        int txtSdt = Integer.parseInt(sdt.getText());
                        String txtKhoa = (String) khoa.getSelectedItem();
                        String txtNganh = (String) nganh.getSelectedItem();
                        ThanhVien tV = new ThanhVien(thanhVien.getMaTV(), txtName, txtKhoa, txtNganh, txtSdt);
                        tvBLL.updateThanhVien(tV);
                        dispose();
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ThanhVienDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        switch (type) {
            case "create" ->
                bottom.add(btnAdd);
            case "update" ->
                bottom.add(btnEdit);
            case "detail" -> {
                name.setDisable();
                sdt.setDisable();
                khoa.setDisable();
                nganh.setDisable();
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
    
    boolean ValidationInput() throws ParseException {
        if (Validation.isEmpty(name.getText())) {
            JOptionPane.showMessageDialog(this, "Tên thành viên không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (name.getText().length() < 6) {
            JOptionPane.showMessageDialog(this, "Tên thành viên ít nhất 6 kí tự!");
            return false;
        } //        else if (Validation.isEmpty(email.getText()) || !Validation.isEmail(email.getText())) {
        //            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
        //            return false;
        //        }
        else if (Validation.isEmpty(sdt.getText()) || !Validation.isNumber(sdt.getText()) || sdt.getText().length() != 10 || !Validation.checkPhone(sdt.getText())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng, phải là 10 ký tự số và bắt đầu = 0", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
//        else if(jcBd.getDate()==null){
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
//            return false;
//        } else if(!male.isSelected() && !female.isSelected()){
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
//            return false;
//        }

        return true;
    }
}
