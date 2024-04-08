/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BLL.ThanhVienBLL;
import BLL.XuLyBLL;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.ThanhVienDialog;
import GUI.Dialog.XuLyDialog;
import GUI.Main;
import hibernatemember.DAL.ThanhVien;
import hibernatemember.DAL.XuLy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class XuLyPanel extends JPanel implements ActionListener {

    private XuLyBLL xuLyBLL;
    private ThanhVienBLL thanhVienBLL;
    private ArrayList<XuLy> listXuLy;

    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableXuLy;
    JScrollPane scrollTableThongTinSuDung;
    MainFunction mainFunction;
    public IntegratedSearch search;
    Main m;

    Color BackgroundColor = new Color(240, 247, 250);
    private DefaultTableModel tblModel;

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở giữa mà contentCenter không bị dính sát vào các thành phần khác
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentCenter.add(functionBar, BorderLayout.NORTH);

        String[] action = {"create", "update", "delete"};
        mainFunction = new MainFunction(action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
//        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);

        tableXuLy = new JTable();
        scrollTableThongTinSuDung = new JScrollPane();
        tableXuLy = new JTable();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã Xử Lý", "Mã Thành Viên", "Tên Thành Viên", "Hình Thức Xử Lý", "Số Tiền", "Ngày Xử Lý", "Trạng Thái Xử Lý"};

        tblModel.setColumnIdentifiers(header);
        tableXuLy.setModel(tblModel);
        tableXuLy.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableXuLy.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableXuLy.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableXuLy.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableXuLy.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableXuLy.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableXuLy.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableXuLy.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        scrollTableThongTinSuDung.setViewportView(tableXuLy);
        main.add(scrollTableThongTinSuDung);
    }

    public XuLyPanel(Main m) {
        xuLyBLL = new XuLyBLL();
        listXuLy = xuLyBLL.LoadXuLy();
        thanhVienBLL = new ThanhVienBLL();

        this.m = m;
        initComponent();
        tableXuLy.setDefaultEditor(Object.class, null);

        loadDataTable(listXuLy);
    }

    public int getRow() {
        return tableXuLy.getSelectedRow();
    }
    
    public XuLy getXuLy(){
        return listXuLy.get(tableXuLy.getSelectedRow());
    }

    public void loadDataTable(ArrayList<XuLy> listXuLy) {
        tblModel.setRowCount(0);
        for (XuLy xuLy : listXuLy) {
            ThanhVien thanhVien = thanhVienBLL.getThanhVien(xuLy.getMaTV());
            String tenThanhVien = thanhVien.getHoTen();
            String trangThaiXuLy = (xuLy.getTrangThaiXL() == 0) ? "Đã Xử Lý" : "Đang Xử Lý";
            tblModel.addRow(new Object[]{
                xuLy.getMaXL(), xuLy.getMaTV(), tenThanhVien, xuLy.getHinhThucXL(), xuLy.getSoTien(), xuLy.getNgayXL(), trangThaiXuLy
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        switch (btn) {
            case "THÊM" -> {
                XuLyDialog xlthem = new XuLyDialog(owner, true, "Thêm xử lý", "create");
            }
            case "SỬA" -> {
                int index = getRow();
                if (index != -1) {
                    XuLyDialog xuLyDialog = new XuLyDialog(owner, true, "Sửa xử lý", "update", getXuLy());
                }
            }
            case "XÓA" -> {
                int index = getRow();
                if (index != -1) {
                    int input = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắn muốn xóa xử lý!", "Xóa xử lý",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (input == 0) {
                        xuLyBLL.DeleteXuLy(getXuLy());
                    }
                }
            }
        }
        listXuLy = xuLyBLL.LoadXuLy();
        loadDataTable(listXuLy);
    }
}
