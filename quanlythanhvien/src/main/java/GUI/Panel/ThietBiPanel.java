/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Panel;

import BLL.ThietBiBLL;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.PanelBorderRadius;
import GUI.Main;
import hibernatemember.DAL.ThietBi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL
 */
public final class ThietBiPanel extends JPanel {

    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private final ThietBiBLL thietBiBLL = new ThietBiBLL(this);
    PanelBorderRadius main, functionBar;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableThietBi;
    JScrollPane scrollTableThietBi;
    MainFunction mainFunction;
    public IntegratedSearch search;
    Main m;

    Color BackgroundColor = new Color(240, 247, 250);
    private DefaultTableModel tblModel;

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

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

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentCenter.add(functionBar, BorderLayout.NORTH);

        String[] action = {"create", "update", "delete", "detail", "import", "export"};
        mainFunction = new MainFunction(action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(thietBiBLL);
        }
        functionBar.add(mainFunction);
        search = new IntegratedSearch(new String[]{"Tất cả", "Mã Thiết bị", "Tên Thiết bị", "Mô tả Thiết bị"});
        functionBar.add(search);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        tableThietBi = new JTable();
        scrollTableThietBi = new JScrollPane();
        tableThietBi = new JTable();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã TB", "Tên TB", "Mô tả TB"};

        tblModel.setColumnIdentifiers(header);
        tableThietBi.setModel(tblModel);
        tableThietBi.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableThietBi.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableThietBi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableThietBi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableThietBi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        scrollTableThietBi.setViewportView(tableThietBi);
        main.add(scrollTableThietBi);
    }

    public ThietBiPanel(Main m) {
        this.m = m;
        initComponent();
        tableThietBi.setDefaultEditor(Object.class, null);
        loadDataTable();
    }

    public int getRow() {
        return tableThietBi.getSelectedRow();
    }

    public void loadDataTable() {
        ArrayList<ThietBi> list = new ArrayList<>(thietBiBLL.loadThietBi());
        tblModel.setRowCount(0);
        for (ThietBi thietbi : list) {
            tblModel.addRow(new Object[]{
                thietbi.getMaTB(), thietbi.getTenTB(), thietbi.getMoTaTB()
            });
        }
    }

    public int getMa() {
        return (int) tableThietBi.getValueAt(getRow(), 0);
    }

    public String getTen() {
        return tableThietBi.getValueAt(getRow(), 1).toString();
    }

    public String getMota() {
        return tableThietBi.getValueAt(getRow(), 2).toString();
    }

    public void exportToExcel(File file) {
        try {
            TableModel model = tableThietBi.getModel();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet 1");
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();

            // Set font to Times New Roman
            font.setFontName("Times New Roman");
            style.setFont(font);

            // Write column headers
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
                cell.setCellStyle(style);
            }

            // Write data rows
            for (int i = 0; i < model.getRowCount(); i++) {
                XSSFRow dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    XSSFCell cell = dataRow.createCell(j);
                    cell.setCellValue(model.getValueAt(i, j).toString());
                    cell.setCellStyle(style);
                }
            }

            // Write to file
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất ra Excel thành công!");
        } catch (IOException e) {
        }
    }

}
