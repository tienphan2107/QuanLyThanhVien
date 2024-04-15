/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BLL.ThongTinSuDungBLL;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.KhuTuHocDialog;
import GUI.Dialog.MuonThietBiDialog;
import GUI.Dialog.ThanhVienDialog;
import GUI.Dialog.TraThietBiDialog;
import GUI.Main;
import hibernatemember.DAL.ThongTinSuDung;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ThongTinSuDungPanel extends JPanel implements ActionListener {

    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
//    NhanVienBUS nvBus = new NhanVienBUS(this);
    private ThongTinSuDungBLL thongtinsudungBLL = new ThongTinSuDungBLL();
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableThongTinSuDung;
    JScrollPane scrollTableThongTinSuDung;
    MainFunction mainFunction;
    public IntegratedSearch search;
    Main m;
//    ArrayList<DTO.NhanVienDTO> listnv = nvBus.getAll();

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

        String[] action = {"vaokhutuhoc", "muon", "tra", "datcho"};
        mainFunction = new MainFunction(action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
//        search = new IntegratedSearch(new String[]{"Tất cả", "Họ tên", "Email"});
//        functionBar.add(search);
//        search.btnReset.addActionListener(nvBus);
//        search.cbxChoose.addActionListener(nvBus);
//        search.txtSearchForm.getDocument().addDocumentListener(new NhanVienBUS(search.txtSearchForm, this));

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
//        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);

        tableThongTinSuDung = new JTable();
        scrollTableThongTinSuDung = new JScrollPane();
        tableThongTinSuDung = new JTable();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã TT", "Mã TV", "Mã TB", "Thời gian vào", "Thời gian mượn", "Thời gian trả"};

        tblModel.setColumnIdentifiers(header);
        tableThongTinSuDung.setModel(tblModel);
        tableThongTinSuDung.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableThongTinSuDung.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableThongTinSuDung.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableThongTinSuDung.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableThongTinSuDung.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableThongTinSuDung.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableThongTinSuDung.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableThongTinSuDung.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        scrollTableThongTinSuDung.setViewportView(tableThongTinSuDung);
        main.add(scrollTableThongTinSuDung);
    }

    public ThongTinSuDungPanel(Main m) throws ParseException {
        this.m = m;
        initComponent();
        thongtinsudungBLL = new ThongTinSuDungBLL();
        tableThongTinSuDung.setDefaultEditor(Object.class, null);
        loadDataTable();
    }

    public int getRow() {
        int index = tableThongTinSuDung.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thành viên muốn mượn thiết bị ");
        }
        return tableThongTinSuDung.getSelectedRow();
    }

    public int getRow1() {
        int index = tableThongTinSuDung.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thành viên muốn trả thiết bị ");
        }
        return tableThongTinSuDung.getSelectedRow();
    }

    public ThongTinSuDung getThongTin() {
        int MaTT = Integer.parseInt(tableThongTinSuDung.getValueAt(tableThongTinSuDung.getSelectedRow(), 0).toString());
        return thongtinsudungBLL.getThongTinSuDung(MaTT);
    }

//    public DTO.NhanVienDTO getNhanVien() {
//        return listnv.get(tableNhanVien.getSelectedRow());
//    }
    public void loadDataTable() throws ParseException {
        ArrayList<ThongTinSuDung> list = thongtinsudungBLL.LoadThongTinSuDung();
        tblModel.setRowCount(0);
        for (ThongTinSuDung ttsd : list) {
            String maTB = "Không mượn";
            String tgMuon = "Không mượn";
            String tgTra = "Không mượn";
            try {
                maTB = ttsd.getThietBi().getMaTB() + "";
                tgMuon = ttsd.getTGMuon().toString();
                tgTra = ttsd.getTGTra().toString();
            } catch (Exception e) {
                if (maTB != "Không mượn") {
                    tgTra = "Chưa trả";
                }
            }
            tblModel.addRow(new Object[]{
                ttsd.getMaTT(), ttsd.getThanhVien().getMaTV(), maTB, ttsd.getTGVao(), tgMuon, tgTra
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        switch (btn) {
            case "VÀO KHU HỌC TẬP" -> {
                KhuTuHocDialog tgKhuTuHoc = new KhuTuHocDialog(owner, true, "Vào khu tự học", "create");
            }
            case "MƯỢN" -> {
                int index = getRow();
                if (index != -1) {
                    MuonThietBiDialog muon = new MuonThietBiDialog(owner, true, "Mượn thiết bị", "create", getThongTin());
                }
            }
            case "TRẢ" -> {
                int index = getRow1();
                if (index != -1) {
                    TraThietBiDialog tra = new TraThietBiDialog(owner, true, "Trả thiết bị", "create", getThongTin());

//                    TraThietBiDialog tra = new TraThietBiDialog(owner, true, "Trả thiết bị", "create", getThongTin());
                }
            }
            case "ĐẶT CHỔ" -> {
//                int index = getRow();
//                if (index != -1) {
//                    ThanhVienDialog nvsua = new ThanhVienDialog(owner, true, "Xem nhân viên", "detail", getThanhVien());
//                }
            }
        }

        try {
            loadDataTable();
        } catch (ParseException ex) {
            Logger.getLogger(ThongTinSuDungPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
