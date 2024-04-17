/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Dialog;

/**
 *
 * @author nguye
 */
import BLL.ThietBiBLL;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputForm;
import GUI.Component.SelectForm;
import GUI.Panel.ThietBiPanel;
import com.formdev.flatlaf.extras.components.FlatRadioButton;
import helper.Validation;
import hibernatemember.DAL.ThietBi;
import hibernatemember.DAL.ThietBiDAL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class ThietBiDialog extends JDialog {
    private ThietBiBLL tb = new ThietBiBLL();
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit, btnDel;
    private InputForm maTB, tenTB, moTaTB;
    private FlatRadioButton del, dels;
    private SelectForm fdel, fdels;
    private ThietBi thietBi;
    

    public ThietBiDialog(JFrame owner, boolean modal, String title, String type, ThietBi thietBi ){
        super(owner, title, modal);
        this.thietBi=thietBi;
        init( title, type);
        maTB.setText(String.valueOf(thietBi.getMaTB()));
        tenTB.setText(thietBi.getTenTB());
        moTaTB.setText(thietBi.getMoTaTB());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public ThietBiDialog(JFrame owner, boolean modal, String title, String type){
        super(owner, title, modal);
        init( title, type);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void init( String title, String type) {
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);
        maTB = new InputForm("Mã thiết bị");
        tenTB = new InputForm("Tên thiết bị");
        moTaTB = new InputForm("Mô tả thiết bị");

        del = new FlatRadioButton();
        del.setText("Xóa 1 thiết bị");
        dels = new FlatRadioButton();
        dels.setText("Xóa nhiều thiết bị");

        ButtonGroup group = new ButtonGroup();
        group.add(del);
        group.add(dels);
        del.setSelected(true);

        String[] thietbiList = tb.getThietBiStrings();
        String[] thietbiLoai = new String[6];
        thietbiLoai[0] = "1 - Micro";
        thietbiLoai[1] = "2 - Máy chiếu";
        thietbiLoai[2] = "3 - Máy ảnh";
        thietbiLoai[3] = "4 - Cassette";
        thietbiLoai[4] = "5 - Tivi";
        thietbiLoai[5] = "6 - Quạt đứng";
        fdel = new SelectForm(del, thietbiList);
        fdels = new SelectForm(dels, thietbiLoai);
        fdels.setDisable();

        JPanel jpanelG = new JPanel(new GridLayout(3, 1, 0, 2));
        jpanelG.setBackground(Color.white);
        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm thiết bị", "success", 14);
        btnEdit = new ButtonCustom("Lưu thông tin", "success", 14);
        btnDel = new ButtonCustom("Xóa thiết bị", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener((ActionEvent e) -> {
            dispose();
        });
        
        tenTB.getTxtForm().addActionListener((ActionEvent e) -> {
            maTB.setText("" + tb.generateDeviceCode(tenTB.getText()));
            moTaTB.requestFocus();
        });

        btnAdd.addActionListener((ActionEvent e) -> {
            if (ValidationInput()) {
                int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    //Integer.valueOf(tb.generateDeviceCode(tenTB.getText()));
                    tb.addThietBi(new ThietBi(Integer.parseInt(maTB.getText()), tenTB.getText(), moTaTB.getText()));

                    JOptionPane.showMessageDialog(this, "Thêm thành công", "Xác nhận !", JOptionPane.INFORMATION_MESSAGE);
                    maTB.setText("");
                    tenTB.setText("");
                    moTaTB.setText("");
                }
            }
        });

        btnEdit.addActionListener((ActionEvent e) -> {
            if (ValidationInput()) {
                int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    tb.updateThietBi(new ThietBi(Integer.parseInt(maTB.getText()), tenTB.getText(), moTaTB.getText()));
                    JOptionPane.showMessageDialog(this, "Sửa thành công", "Xác nhận !", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }
            }
        });

        del.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fdels.setDisable();
                fdel.setEnable();
            }
        });

        dels.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                fdel.setDisable();
                fdels.setEnable();
            }
        });

        btnDel.addActionListener((ActionEvent e) -> {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (del.isSelected()) {
                    String getMa = fdel.getSelectedItem().toString();
                    String[] spli = getMa.split(" - ");
                    int ma = Integer.parseInt(spli[0]);
                    tb.deleteThietBi(ma);
                    JOptionPane.showMessageDialog(this, "Xóa thành công", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                    this.dispose();
                } else if (dels.isSelected()) {
                    String loai = fdels.getSelectedItem().toString().charAt(0) + "";
                    tb.deleteThietBiByStartingNumber(loai);
                    JOptionPane.showMessageDialog(this, "Xóa thành công", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                    this.dispose();
                }
            }
        });

        switch (type) {
            case "create" -> {
                main.add(maTB);
                main.add(tenTB);
                main.add(moTaTB);
                main.add(jpanelG);

                maTB.setDisable();
                bottom.add(btnAdd);
            }
            case "update" -> {
                main.add(maTB);
                main.add(tenTB);
                main.add(moTaTB);
                tenTB.setDisable();
                main.add(jpanelG);
                


                maTB.setDisable();
                bottom.add(btnEdit);
            }
            case "view" -> {
                main.add(maTB);
                main.add(tenTB);
                main.add(moTaTB);
                main.add(jpanelG);


                maTB.setDisable();
                tenTB.setDisable();
                moTaTB.setDisable();
            }
            case "delete" -> {
                main.add(fdel);
                main.add(fdels);
                main.add(jpanelG);
                bottom.add(btnDel);
            }
            default ->
                throw new AssertionError();
        }

        bottom.add(btnExit);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
    }

    boolean ValidationInput() {
        try {
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Không có loại thiết bị này", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (Validation.isEmpty(moTaTB.getText())) {
            JOptionPane.showMessageDialog(this, "Mô tả thiết bị không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}