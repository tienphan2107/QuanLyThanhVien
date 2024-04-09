/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BLL.ThanhVienBLL;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.ThanhVienDialog;
import GUI.Main;
import helper.Validation;
import hibernatemember.DAL.ThanhVien;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL
 */
public class ThanhVienPanel extends JPanel implements ActionListener {

    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private ThanhVienBLL tvBLL = new ThanhVienBLL();
    ArrayList<ThanhVien> listTV = tvBLL.loadThanhVien();
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
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);
        search = new IntegratedSearch(new String[]{"Tất cả", "Khoa", "Ngành"});
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
        int index = tableThanhVien.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thành viên ");
        }
        return tableThanhVien.getSelectedRow();
    }

    public ThanhVien getThanhVien() {
        return listTV.get(tableThanhVien.getSelectedRow());
    }
//    public void loadDataTalbe(ArrayList<DTO.NhanVienDTO> list) {
//        listnv = list;
//        tblModel.setRowCount(0);
//        for (DTO.NhanVienDTO nhanVien : listnv) {
//            tblModel.addRow(new Object[]{
//                nhanVien.getManv(), nhanVien.getHoten(), nhanVien.getGioitinh() == 1 ? "Nam" : "Nữ", nhanVien.getNgaysinh(), nhanVien.getSdt(), nhanVien.getEmail()
//            });
//        }
//    }

    public void loadDataTable(ArrayList<ThanhVien> list) {
//        ArrayList<ThanhVien> list = new ArrayList<>(tvBLL.loadThanhVien());
        tblModel.setRowCount(0);
        for (ThanhVien thanhVien : list) {
            // Thêm dữ liệu vào bảng tblModel
            tblModel.addRow(new Object[]{
                thanhVien.getMaTV(), thanhVien.getHoTen(), thanhVien.getKhoa(), thanhVien.getNganh(), thanhVien.getSDT()
            });
        }
    }

    public void loadDataTable() {
        ArrayList<ThanhVien> list = new ArrayList<>(tvBLL.loadThanhVien());
        tblModel.setRowCount(0);
        for (ThanhVien thanhVien : list) {
            // Thêm dữ liệu vào bảng tblModel
            tblModel.addRow(new Object[]{
                thanhVien.getMaTV(), thanhVien.getHoTen(), thanhVien.getKhoa(), thanhVien.getNganh(), thanhVien.getSDT()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        switch (btn) {
            case "THÊM" -> {
                ThanhVienDialog tvthem = new ThanhVienDialog(owner, true, "Thêm thành viên", "create");
            }
            case "SỬA" -> {
                int index = getRow();
                if (index != -1) {
                    ThanhVienDialog tvsua = new ThanhVienDialog(owner, true, "Sửa thành viên", "update", getThanhVien());
                }
            }
            case "XÓA" -> {
                int index = getRow();
                if (index != -1) {
                    int input = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắn muốn xóa thành viên!", "Xóa thành viên",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (input == 0) {
                        tvBLL.deleteThanhVien(getThanhVien());
                    }
                }
            }
            case "CHI TIẾT" -> {
                int index = getRow();
                if (index != -1) {
                    ThanhVienDialog nvsua = new ThanhVienDialog(owner, true, "Xem nhân viên", "detail", getThanhVien());
                }
            }
            case "NHẬP EXCEL" -> {
                //importExcel();
            }
        }

        loadDataTable();
    }
    
//    public void importExcel() {
//        File excelFile;
//        FileInputStream excelFIS = null;
//        BufferedInputStream excelBIS = null;
//        XSSFWorkbook excelJTableImport = null;
//        JFileChooser jf = new JFileChooser();
//        int result = jf.showOpenDialog(null);
//        jf.setDialogTitle("Open file");
//        Workbook workbook = null;
//        int k = 0;
//        if (result == JFileChooser.APPROVE_OPTION) {
//            try {
//                excelFile = jf.getSelectedFile();
//                excelFIS = new FileInputStream(excelFile);
//                excelBIS = new BufferedInputStream(excelFIS);
//                excelJTableImport = new XSSFWorkbook(excelBIS);
//                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
//
//                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
//                    int check = 1;
//                    int gt;
//                    XSSFRow excelRow = excelSheet.getRow(row);
//                    int id = NhanVienDAO.getInstance().getAutoIncrement();
//                    String tennv = excelRow.getCell(0).getStringCellValue();
//                    String gioitinh = excelRow.getCell(1).getStringCellValue();
//                    if (gioitinh.equals("Nam") || gioitinh.equals("nam")) {
//                        gt = 1;
//                    } else {
//                        gt = 0;
//                    }
//                    String sdt = excelRow.getCell(3).getStringCellValue();
//                    Date ngaysinh = (Date) excelRow.getCell(2).getDateCellValue();
//                    java.sql.Date birth = new java.sql.Date(ngaysinh.getTime());
//                    String email = excelRow.getCell(4).getStringCellValue();
//                    if (Validation.isEmpty(tennv) || Validation.isEmpty(email)
//                            || !Validation.isEmail(email) || Validation.isEmpty(sdt)
//                            || Validation.isEmpty(sdt) || !isPhoneNumber(sdt)
//                            || sdt.length() != 10 || Validation.isEmpty(gioitinh)) {
//                        check = 0;
//                    }
//                    if (check == 0) {
//                        k += 1;
//                    } else {
//                        NhanVienDTO nvdto = new NhanVienDTO(id, tennv, gt, birth, sdt, 1, email);
//                        NhanVienDAO.getInstance().insert(nvdto);
//                    }
//                    JOptionPane.showMessageDialog(null, "Nhập thành công");
//                }
//
//            } catch (FileNotFoundException ex) {
//                System.out.println("Lỗi đọc file");
//            } catch (IOException ex) {
//                System.out.println("Lỗi đọc file");
//            }
//        }
//        if (k != 0) {
//            JOptionPane.showMessageDialog(null, "Những dữ liệu không chuẩn không được thêm vào");
//        }
//    }
}
