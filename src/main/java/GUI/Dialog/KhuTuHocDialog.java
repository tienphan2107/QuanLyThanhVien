/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

import BLL.ThanhVienBLL;
import BLL.ThongTinSuDungBLL;
import BLL.XuLyBLL;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
public class KhuTuHocDialog extends JDialog {

    private ThanhVienBLL thanhVienBLL = new ThanhVienBLL();
    private ThongTinSuDungBLL thongtinBLL = new ThongTinSuDungBLL();
    private XuLyBLL xuLyBLL = new XuLyBLL();
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm txtMaTV, name;
    SelectForm maTB;
    private InputForm sdt;
    private ThongTinSuDung khuTuHoc;
    private InputDate ipDate;
    String[] arrMaTB = {"Micro", "Micro", "Bảng điện tử"};

    ;

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
        txtMaTV = new InputForm("Mã số sinh viên");
        PlainDocument phonex = (PlainDocument) txtMaTV.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        name = new InputForm("Tên sinh viên");
//        arrMaTB = thongtinBLL.getListMaTB();
//        maTB = new SelectForm("Mã thiết bị", arrMaTB);
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
        JLabel lbBd = new JLabel("Thời gian vào");
        lbBd.setSize(new Dimension(100, 100));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);

        ipDate = new InputDate("Thời gian vào");
        ipDate.setSize(new Dimension(100, 100));
        Date currentDate = new Date(System.currentTimeMillis());
        ipDate.setDate(currentDate);
        jpaneljd.add(lbBd);
        jpaneljd.add(ipDate);
        main.add(txtMaTV);
        main.add(name);
//        main.add(maTB);

//        main.add(jpanelG);
        main.add(ipDate);
//        main.add(jcBd);

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Tham gia", "success", 14);
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

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int maTT = thongtinBLL.getMaTTAutoIncreasement();
                    Date ngay = ipDate.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = dateFormat.parse(ngay.toString());
                    System.out.print(ngay + "Yeahhhhh");
                    int maThanhVien = Integer.parseInt(txtMaTV.getText().trim());
                    ThanhVien thanhVien = thanhVienBLL.getThanhVien(maThanhVien);
                    if (xuLyBLL.ThanhVienViPham(maThanhVien)) 
                    { 
                        JOptionPane.showMessageDialog(rootPane, "Thành viên đang bị xử lý vui lòng thử lại sau");
                        return;
                    }
                    if (thanhVien == null) {
                        JOptionPane.showMessageDialog(rootPane, "Thất bại ! Mã thành viên không hợp lệ");
                        return;
                    }
//                    String formattedDate = dateFormat.format(new Date());
                    ThongTinSuDung thongtin = new ThongTinSuDung(maTT, maThanhVien, null, new Date(), null, null);
                    if (thongtinBLL.newThongTinSuDung(thongtin)) {
                        JOptionPane.showMessageDialog(rootPane, "Thành công !");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Thất bại !");
                    }
                    dispose();
//                    }
                } catch (ParseException ex) {
                    Logger.getLogger(KhuTuHocDialog.class.getName()).log(Level.SEVERE, null, ex);
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
