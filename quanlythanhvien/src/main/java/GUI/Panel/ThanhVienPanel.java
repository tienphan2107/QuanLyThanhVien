/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BLL.ThanhVienBLL;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Main;
import hibernatemember.DAL.ThanhVien;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class ThanhVienPanel extends JPanel {
    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private ThanhVienBLL tvBLL = new ThanhVienBLL(this);
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableThanhVien;
    JScrollPane scrollTableThanhVien;
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

        String[] action = {"create", "update", "delete", "detail", "import", "export"};
        mainFunction = new MainFunction(action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(tvBLL);
        }
        functionBar.add(mainFunction);
        search = new IntegratedSearch(new String[]{"Tất cả","Khoa", "Ngành"});
        functionBar.add(search);
//        search.btnReset.addActionListener(nvBus);
//        search.cbxChoose.addActionListener(nvBus);
//        search.txtSearchForm.getDocument().addDocumentListener(new NhanVienBUS(search.txtSearchForm, this));

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        tableThanhVien = new JTable();
        scrollTableThanhVien = new JScrollPane();
        tableThanhVien = new JTable();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã TV", "Họ tên", "Khoa", "Ngành", "SĐT"};

        tblModel.setColumnIdentifiers(header);
        tableThanhVien.setModel(tblModel);
        tableThanhVien.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableThanhVien.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableThanhVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableThanhVien.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableThanhVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableThanhVien.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableThanhVien.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        scrollTableThanhVien.setViewportView(tableThanhVien);
        main.add(scrollTableThanhVien);
    }

    public ThanhVienPanel(Main m) {
        this.m = m;
        initComponent();
        tableThanhVien.setDefaultEditor(Object.class, null);
        loadDataTable();
    }

    public int getRow() {
        return tableThanhVien.getSelectedRow();
    }

//    public DTO.NhanVienDTO getNhanVien() {
//        return listnv.get(tableNhanVien.getSelectedRow());
//    }
//    public void loadDataTalbe(ArrayList<DTO.NhanVienDTO> list) {
//        listnv = list;
//        tblModel.setRowCount(0);
//        for (DTO.NhanVienDTO nhanVien : listnv) {
//            tblModel.addRow(new Object[]{
//                nhanVien.getManv(), nhanVien.getHoten(), nhanVien.getGioitinh() == 1 ? "Nam" : "Nữ", nhanVien.getNgaysinh(), nhanVien.getSdt(), nhanVien.getEmail()
//            });
//        }
//    }
    public void loadDataTable() {
        ArrayList<ThanhVien> list = new ArrayList<>(tvBLL.loadThanhVien());
        tblModel.setRowCount(0);

        for (ThanhVien thanhVien : list) {
            System.out.print(thanhVien.getKhoa());
            // Thêm dữ liệu vào bảng tblModel
            tblModel.addRow(new Object[]{
                thanhVien.getMaTV(), thanhVien.getHoTen(), thanhVien.getKhoa(), thanhVien.getNganh(), thanhVien.getSDT()
            });
        }
    }
}
