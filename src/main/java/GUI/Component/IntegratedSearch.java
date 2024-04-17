/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import GUI.Panel.ThanhVienPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.accessibility.AccessibleContext;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author DELL
 */
public class IntegratedSearch extends JPanel {

    private GUI.Panel.ThanhVienPanel tvPanel;
    public JComboBox<String> cbxChoose;
    public JButton btnReset;
    public JTextField txtSearchForm;

    private void initComponent(String str[]) {

        this.setBackground(Color.WHITE);
        BoxLayout bx = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bx);

        JPanel jpSearch = new JPanel(new BorderLayout(5, 10));
        jpSearch.setBorder(new EmptyBorder(18, 15, 18, 15));
        jpSearch.setBackground(Color.white);
//        cbxChoose = new JComboBox();
//        cbxChoose.setModel(new DefaultComboBoxModel<>(str));
//        cbxChoose.setPreferredSize(new Dimension(140, 0));
//        cbxChoose.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 0, 13));
//        cbxChoose.setFocusable(false);
//        jpSearch.add(cbxChoose,BorderLayout.WEST);

        txtSearchForm = new JTextField();
        txtSearchForm.setFont(new Font(FlatRobotoFont.FAMILY, 0, 13));
        txtSearchForm.putClientProperty("JTextField.placeholderText", "Tìm kiếm theo mã thành viên...");
        txtSearchForm.putClientProperty("JTextField.showClearButton", true);
        jpSearch.add(txtSearchForm);

        btnReset = new JButton("Làm mới");
        btnReset.setFont(new java.awt.Font(FlatRobotoFont.FAMILY, 0, 14));
        btnReset.setIcon(new FlatSVGIcon("./icon/refresh.svg"));
        btnReset.setPreferredSize(new Dimension(125, 0));
        btnReset.addActionListener(this::btnResetActionPerformed);
        jpSearch.add(btnReset, BorderLayout.EAST);
        this.add(jpSearch);
    }

    public IntegratedSearch(String str[]) {
        initComponent(str);
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent e) {
        txtSearchForm.setText("");
        if (cbxChoose != null) {
            cbxChoose.setSelectedIndex(0);
        }

    }

    public ThanhVienPanel getTvPanel() {
        return tvPanel;
    }

    public void setTvPanel(ThanhVienPanel tvPanel) {
        this.tvPanel = tvPanel;
    }

    public JComboBox<String> getCbxChoose() {
        return cbxChoose;
    }

    public void setCbxChoose(JComboBox<String> cbxChoose) {
        this.cbxChoose = cbxChoose;
    }

    public JTextField getTxtSearchForm() {
        return txtSearchForm;
    }

    public void setTxtSearchForm(JTextField txtSearchForm) {
        this.txtSearchForm = txtSearchForm;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    public void setAccessibleContext(AccessibleContext accessibleContext) {
        this.accessibleContext = accessibleContext;
    }

}
