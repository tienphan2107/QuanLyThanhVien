/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import hibernatemember.DAL.ThanhVien;
import hibernatemember.DAL.ThongTinSuDung;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

/**
 *
 * @author DELL
 */
public class KhuTuHocDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm mssv;
    SelectForm khoa, nganh;
    private InputForm sdt;
    private ThongTinSuDung khuTuHoc;
    
    public KhuTuHocDialog(JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(title, type);
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
        mssv = new InputForm("Mã số sinh viên");
        
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
        main.add(mssv);
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
        
//        btnAdd.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (ValidationInput()) {
//                        
//                        int manv =Integer.parseInt(tvBLL.createMaTV());
//                        String txtName = name.getText();
//                        String txtSdt = sdt.getText();
//                        String txtKhoa = (String) khoa.getSelectedItem();
//                        String txtNganh = (String) nganh.getSelectedItem();
//                        ThanhVien tV = new ThanhVien(manv, txtName, txtKhoa, txtNganh, txtSdt);
//                        tvBLL.newThanhVien(tV);
//                        dispose();
//                    }
//                } catch (ParseException ex) {
//                    Logger.getLogger(ThanhVienDialog.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
        
//        btnEdit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (ValidationInput()) {
//                        String txtName = name.getText();
//                        String txtSdt = sdt.getText();
//                        String txtKhoa = (String) khoa.getSelectedItem();
//                        String txtNganh = (String) nganh.getSelectedItem();
//                        ThanhVien tV = new ThanhVien(thanhVien.getMaTV(), txtName, txtKhoa, txtNganh, txtSdt);
//                        tvBLL.updateThanhVien(tV);
//                        dispose();
//                    }
//                } catch (ParseException ex) {
//                    Logger.getLogger(ThanhVienDialog.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        switch (type) {
//            case "create" ->
//                bottom.add(btnAdd);
//            case "update" ->
//                bottom.add(btnEdit);
//            case "detail" -> {
//                name.setDisable();
//                sdt.setDisable();
//                khoa.setDisable();
//                nganh.setDisable();
////                email.setDisable();
////                Enumeration<AbstractButton> enumeration = gender.getElements();
////                while (enumeration.hasMoreElements()) {
////                    enumeration.nextElement().setEnabled(false);
////                }
////                jcBd.setDisable();
//            }
//            default ->
//                throw new AssertionError();
//        }
        bottom
                .add(btnExit);
        
        this.add(titlePage, BorderLayout.NORTH);
        
        this.add(main, BorderLayout.CENTER);
        
        this.add(bottom, BorderLayout.SOUTH);
        
    }
}
