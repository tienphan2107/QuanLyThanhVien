package GUI.Panel;

import BLL.XuLyBLL;
import GUI.Component.InputForm;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import POJO.DateRange;
import helper.ComboBoxDateValues;
import helper.CustomTableCellRenderer;
import helper.DateHelper;
import helper.NumberHelper;
import helper.SelectDateCallback;
import hibernatemember.DAL.XuLy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ThongKeViPhamPanel extends JPanel implements SelectDateCallback {

    private final XuLyBLL xlBLL;
    private final String[] comboboxDateValues;
    private final Color backgroundColor = new Color(240, 247, 250);
    private PanelBorderRadius pbrFilter, pbrTable;
    private JPanel pFilter, pTable;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private SelectForm selectType, selectDateRange;
    private InputForm inputName;
    private DateRange dateRange;
    private String query;
    private int state;

    public ThongKeViPhamPanel(XuLyBLL xlBLL, String[] comboboxDateValues) {
        this.xlBLL = xlBLL;
        this.comboboxDateValues = comboboxDateValues;
        this.query = "";
        this.state = 0;
        // Set fromDate to 7 days ago, toDate to today
        LocalDateTime fromDate = LocalDateTime.now().minusDays(6);
        this.dateRange = new DateRange(fromDate, LocalDateTime.now());
        initComponent();
        this.selectDateRange.getLblTitle().setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
        this.getStat(this.state, this.dateRange, this.query);
    }

    private void initComponent() {
        this.setBackground(backgroundColor);
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);

        this.pbrFilter = new PanelBorderRadius();
        this.pbrFilter.setLayout(new BorderLayout());

        this.pFilter = new JPanel(new GridLayout(1, 3, 0, 5));
        this.pFilter.setBackground(Color.WHITE);
        this.pFilter.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.selectType = new SelectForm("Tình trạng", new String[]{"Đã xử lý", "Đang xử lý"});
        this.selectDateRange = new SelectForm("dd/mm/yyyy - dd/mm/yyyy", this.comboboxDateValues);
        this.inputName = new InputForm("Tên thành viên");

        this.pFilter.add(this.selectType);
        this.pFilter.add(this.selectDateRange);
        this.pFilter.add(this.inputName);

        this.pbrTable = new PanelBorderRadius();
        this.pbrTable.setLayout(new BorderLayout());

        this.pTable = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.table = new JTable();
        this.tableModel = new DefaultTableModel();
        String[] header = new String[]{"Mã thành viên", "Tên thành viên", "Hình thức xử lý", "Bồi thường", "Ngày xử lý", "Trạng thái"};
        this.tableModel.setColumnIdentifiers(header);
        this.table.setModel(this.tableModel);
//        this.table.setAutoCreateRowSorter(true);
        this.table.setDefaultEditor(Object.class, null);
        this.table.setFocusable(false);

        this.scrollPane.setViewportView(this.table);

        this.table.getColumnModel().getColumn(0).setPreferredWidth(24);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(160);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(160);
        this.table.getColumnModel().getColumn(3).setPreferredWidth(40);
        this.table.getColumnModel().getColumn(5).setPreferredWidth(24);
        this.table.getColumnModel().getColumn(3).setCellRenderer(CustomTableCellRenderer.RIGHT);
        this.table.getColumnModel().getColumn(4).setCellRenderer(CustomTableCellRenderer.CENTER);
        this.pTable.add(this.scrollPane);
        this.pbrFilter.add(this.pFilter, BorderLayout.CENTER);
        this.pbrTable.add(this.pTable, BorderLayout.CENTER);

        this.add(this.pbrFilter, BorderLayout.NORTH);
        this.add(this.pbrTable, BorderLayout.CENTER);

        this.selectType.getCbb().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleComboBoxTypeChanged();
            }
        });

        this.selectDateRange.getCbb().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleComboBoxOptionChanged();
            }
        });

        this.inputName.getTxtForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleInputChanged();
            }
        });
    }

    private void handleComboBoxTypeChanged() {
        int selectedIndex = this.selectType.getSelectedIndex();
        if (this.state == selectedIndex) {
            return;
        }
        this.state = selectedIndex;
        getStat(this.state, this.dateRange, this.query);
    }

    private void handleComboBoxOptionChanged() {
        boolean changed = ComboBoxDateValues.handleComboBoxChanged(this, String.valueOf(this.selectDateRange.getCbb().getSelectedItem()), this.dateRange, this);
        if (changed) {
            this.selectDateRange.getLblTitle().setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
            getStat(this.state, this.dateRange, this.query);
        }
    }

    private void handleInputChanged() {
        if (inputName.getText().trim().toLowerCase().equals(query.toLowerCase())) {
            return;
        }
        this.query = inputName.getText().trim();
        getStat(this.state, this.dateRange, this.query);
    }

    private void getStat(int state, DateRange dateRange, String query) {
        ArrayList<XuLy> list = xlBLL.getStatXuLy(state, dateRange, query);
        this.tableModel.setRowCount(0);
        if (state == 0) {
            Long total = xlBLL.tongTienBoiThuong(list);
            Object[] totalRow = {"Tổng", "", "", NumberHelper.appendVND(NumberHelper.commafy(total)), "", ""};
            this.tableModel.addRow(totalRow);
        }
        for (XuLy i : list) {
            String strAmount = NumberHelper.appendVND(NumberHelper.commafy(Long.valueOf(i.getSoTien().toString())));
            LocalDateTime ldtNgayXL = DateHelper.convertDateObjToLDT(i.getNgayXL());
            String strNgayXL = ldtNgayXL.format(DateHelper.DATE_TIME_FORMATTER);
            String strState = i.getTrangThaiXL() == 0 ? "Đã xử lý" : "Đang xử lý";
            Object[] row = {i.getThanhVien().getMaTV(), i.getThanhVien().getHoTen(), i.getHinhThucXL(), strAmount, strNgayXL, strState};
            this.tableModel.addRow(row);
        }
    }

    @Override
    public void onDateRangeSelected(DateRange dateRange) {
        this.dateRange = dateRange;
        this.selectDateRange.getLblTitle().setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
        getStat(this.state, this.dateRange, this.query);
    }
}
