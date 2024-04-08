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

    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit, btnDel;
    private InputForm maTB, moTaTB;
    private FlatRadioButton del, dels;
    private SelectForm fdel, fdels, tenTB;
    private final ThietBiDAL tbDAL = new ThietBiDAL();
    int id;

    public ThietBiDialog(ThietBiBLL tb, JFrame owner, boolean modal, String title, String type) {
        super(owner, title, modal);
        init(tb, title, type);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void init(ThietBiBLL tb, String title, String type) {
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(title.toUpperCase());

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);
        maTB = new InputForm("Mã thiết bị");
        String[] tentb = new String[7];
        tentb[0] = "Micro";
        tentb[1] = "Máy chiếu";
        tentb[2] = "Máy ảnh";
        tentb[3] = "Cassette";
        tentb[4] = "Tivi";
        tentb[5] = "Quạt đứng";
        tentb[6] = "Bảng điện tử";
        tenTB = new SelectForm("Tên thiết bị", tentb);
        moTaTB = new InputForm("Mô tả thiết bị");

        del = new FlatRadioButton();
        del.setText("Xóa 1 thiết bị");
        dels = new FlatRadioButton();
        dels.setText("Xóa nhiều thiết bị");

        ButtonGroup group = new ButtonGroup();
        group.add(del);
        group.add(dels);
        del.setSelected(true);

        String[] thietbiList = tbDAL.getThietBiStrings();
        String[] tenList = tbDAL.getTenThietBi();
        fdel = new SelectForm(del, thietbiList);
        fdels = new SelectForm(dels, tenList);
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

        btnAdd.addActionListener((ActionEvent e) -> {
            if (ValidationInput()) {
                tbDAL.addThietBi(new ThietBi(id, tenTB.getSelectedItem().toString(), moTaTB.getText()));

                id = tbDAL.getAutoIncrement();
                maTB.setText("" + id);
                tenTB.setSelectedIndex(0);
                moTaTB.setText("");

                tb.loadThietBi();
            }
        });

        btnEdit.addActionListener((ActionEvent e) -> {
            if (ValidationInput()) {
                tbDAL.updateThietBi(tb.getMa(), tenTB.getSelectedItem().toString(), moTaTB.getText());

                this.dispose();
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
            if (del.isSelected()) {
                String[] spli = fdel.getSelectedItem().toString().split(" - ");
                int ma = Integer.parseInt(spli[0]);
                tbDAL.deleteThietBi(ma);

                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                this.dispose();
            } else if (dels.isSelected()) {
                String[] spli = fdels.getSelectedItem().toString().split(" - ");
                String ten = spli[0];
                tbDAL.deleteByTenTB(ten);

                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                this.dispose();
            }
        });

        switch (type) {
            case "create" -> {
                main.add(maTB);
                main.add(tenTB);
                main.add(moTaTB);
                main.add(jpanelG);

                id = tbDAL.getAutoIncrement();
                maTB.setText("" + id);

                maTB.setDisable();
                bottom.add(btnAdd);
            }
            case "update" -> {
                main.add(maTB);
                main.add(tenTB);
                main.add(moTaTB);
                main.add(jpanelG);

                maTB.setText("" + tb.getMa());
                for (int i = 0; i < tentb.length; i++) {
                    if (tentb[i].equals(tb.getTen())) {
                        tenTB.setSelectedIndex(i);
                    }
                }
                moTaTB.setText(tb.getMota());

                maTB.setDisable();
                bottom.add(btnEdit);
            }
            case "view" -> {
                main.add(maTB);
                main.add(tenTB);
                main.add(moTaTB);
                main.add(jpanelG);

                maTB.setText("" + tb.getMa());
                for (int i = 0; i < tentb.length; i++) {
                    if (tentb[i].equals(tb.getTen())) {
                        tenTB.setSelectedIndex(i);
                    }
                }
                moTaTB.setText(tb.getMota());

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
        if (Validation.isEmpty(moTaTB.getText())) {
            JOptionPane.showMessageDialog(this, "Mô tả thiết bị không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
