/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BLL.ThanhVienBLL;
import BLL.ThietBiBLL;
import BLL.ThongTinSuDungBLL;
import BLL.XuLyBLL;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import GUI.Component.SelectForm;
import hibernatemember.DAL.ThanhVien;
import hibernatemember.DAL.ThietBi;
import hibernatemember.DAL.ThongTinSuDung;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
public class MuonThietBiDialog extends JDialog {

    private ThanhVienBLL thanhVienBLL = new ThanhVienBLL();
    private ThongTinSuDungBLL thongtinBLL = new ThongTinSuDungBLL();
    private XuLyBLL xuLyBLL = new XuLyBLL();
    private ThietBi thietBi = new ThietBi();
    private ThietBiBLL thietBiBLL = new ThietBiBLL();
    private ThongTinSuDung thongTin;
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm txtMaTV, name, txtMaTB, motaTB;
    SelectForm tenTB;
//    private InputForm sdt;
    private ThongTinSuDung khuTuHoc;
    private InputDate ipDate;
//    ArrayList<String> ListLoaiThietBi = thietBiBLL.getDanhSachLoaiThietBi();
//    String[] arrMaTB = ListLoaiThietBi .toArray(new String[ListLoaiThietBi .size()]);
    String[] arrTenTB = thietBiBLL.getListTenTB();

    public MuonThietBiDialog(JFrame owner, boolean modal, String title, String type) {
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
        txtMaTV = new InputForm("Mã số sinh viên");

        name = new InputForm("Tên sinh viên");
//        arrMaTB = thongtinBLL.getListMaTB();
//        tenTB = new SelectForm("Tên thiết bị", arrTenTB);
        txtMaTB = new InputForm("Mã thiết bị");
        motaTB = new InputForm("Mô tả thiết bị");
//        male = new JRadioButton("Nam");
//        female = new JRadioButton("Nữ");
//        gender = new ButtonGroup();
//        gender.add(male);
//        gender.add(female);
//        JPanel jpanelG = new JPanel(new GridLayout(2, 1, 0, 2));
//        jpanelG.setBackground(Color.white);
//        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lbBd = new JLabel("Thời gian mượn");
        lbBd.setSize(new Dimension(100, 100));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);

        ipDate = new InputDate("Thời gian mượn");
        ipDate.setSize(new Dimension(100, 100));
        Date currentDate = new Date(System.currentTimeMillis());
        ipDate.setDate(currentDate);
        jpaneljd.add(lbBd);
        jpaneljd.add(ipDate);
        main.add(txtMaTV);
        main.add(name);
        main.add(txtMaTB);
        main.add(motaTB);
//        main.add(tenTB);

//        main.add(jpanelG);
        main.add(ipDate);
//        main.add(jcBd);

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Mượn", "success", 14);
        btnEdit = new ButtonCustom("Lưu thông tin", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        txtMaTV.getTxtForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maThanhVien = Integer.parseInt(txtMaTV.getText().trim());
                    ThanhVien thanhVien = thanhVienBLL.getThanhVien(maThanhVien);
                    if (thanhVien != null) {
                        name.setText(thanhVien.getHoTen());
                    } else {
                        name.setText("Không tìm thấy thành viên có mã này !");
                    }
                } catch (Exception ex) {
                    name.setText("Không tìm thấy thành viên có mã này !");
                }

                txtMaTV.getTxtForm().transferFocus();
            }
        });

        txtMaTB.getTxtForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maThietBi = Integer.parseInt(txtMaTB.getText().trim());
                    ThietBi thietBi = thietBiBLL.getThietBi(maThietBi);
                    if (thietBi != null) {
                        motaTB.setText(thietBi.getMoTaTB());
                    } else {
                        motaTB.setText("Không tìm thấy thiết bị có mã này !");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    motaTB.setText("Không tìm thấy thiết bị có mã này !");
                }

                txtMaTB.getTxtForm().transferFocus();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int maTT = thongtinBLL.getMaTTAutoIncreasement();
                    Date ngay = ipDate.getDate();
                    int maThanhVien = Integer.parseInt(txtMaTV.getText().trim());
                    ThanhVien thanhVien = thanhVienBLL.getThanhVien(maThanhVien);

                    int maThietBi = Integer.parseInt(txtMaTB.getText().trim());
                    if (xuLyBLL.ThanhVienViPham(maThanhVien)) {
                        JOptionPane.showMessageDialog(null, "Thành viên đang bị xử lý vui lòng thử lại sau");
                        return;
                    }
                    if(thongtinBLL.thietBiDaDuocDatCho(maThietBi, maThanhVien)){
                        JOptionPane.showMessageDialog(null, "Thiết bị này đã được người khác đặt chổ trong hôm nay, vui lòng mượn lúc khác");
                        return;
                    }
                    if(thongtinBLL.thietBiDangDuocMuon(maThietBi)){
                        JOptionPane.showMessageDialog(null, "Thiết bị này đang được mượn, vui lòng mượn lúc khác");
                        return;
                    }
                    Integer MaTB = (Integer) maThietBi;
                    if (thanhVien == null) {
                        JOptionPane.showMessageDialog(rootPane, "Thất bại ! Mã thành viên không hợp lệ");
                        return;
                    }
                    
                    ThongTinSuDung thongtin = new ThongTinSuDung(maTT, maThanhVien, MaTB, null, new Date(), null);
                    if (thongtinBLL.newThongTinSuDung(thongtin)) {
                        JOptionPane.showMessageDialog(rootPane, "Thành công !");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Thất bại !");
                    }
                    dispose();
//                    }
                } catch (ParseException ex) {
                    Logger.getLogger(MuonThietBiDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        switch (type) {
            case "create" -> {
                name.setDisable();
                motaTB.setDisable();
                ipDate.setDisable();
                bottom.add(btnAdd);
            }
            case "update" ->
                bottom.add(btnEdit);
            default ->
                throw new AssertionError();
        }
        bottom
                .add(btnExit);

        this.add(titlePage, BorderLayout.NORTH);

        this.add(main, BorderLayout.CENTER);

        this.add(bottom, BorderLayout.SOUTH);

    }
}
