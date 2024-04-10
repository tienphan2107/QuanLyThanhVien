package GUI.Panel;

import BLL.ThanhVienBLL;
import BLL.ThongTinSuDungBLL;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.DialogChoose;
import GUI.KhuHocTapChart;
import POJO.DateRange;
import POJO.ThongKeKhuHocTap;
import helper.ComboBoxDateValues;
import helper.CustomTableCellRenderer;
import helper.DateHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import helper.SelectDateCallback;
import java.awt.Cursor;

public class ThongKeKhuHocTapPanel extends JPanel implements SelectDateCallback {

    private final ThanhVienBLL tvBLL;
    private final ThongTinSuDungBLL ttsdBLL;
    private final Color backgroundColor = new Color(240, 247, 250);
    private PanelBorderRadius pbrFilter, pbrTable;
    private JPanel pFilter, pKhoa, pNganh, pButtons, pTable;
    private JLabel lblDateRange, lblGroupBy, lblKhoa, lblNganh;
    private JButton btnChooseKhoa, btnChooseNganh, btnViewChart, btnGetStat;
    private JTextField txtKhoa, txtNganh;
    private JComboBox cbDateRange, cbGroupBy;
    private JScrollPane scrollPane, spMessageOptionChanged;
    private JTable table;
    private DefaultTableModel tableModel;
    private final String[] comboboxDateValues, comboboxGroupByValues;
    private DateRange dateRange;
    private String khoaQuery;
    private String nganhQuery;
    private JTextArea taOptionChanged;
    private String groupBy;
    private final ChonKhoaCallback chonKhoaCallback;
    private final ChonNganhCallback chonNganhCallback;
    private ArrayList<ThongKeKhuHocTap> list = new ArrayList<>();

    public ThongKeKhuHocTapPanel(ThanhVienBLL tvBLL, ThongTinSuDungBLL ttsdBLL, String[] comboboxDateValues) {
        this.tvBLL = tvBLL;
        this.ttsdBLL = ttsdBLL;
        this.comboboxDateValues = comboboxDateValues;
        this.comboboxGroupByValues = ComboBoxDateValues.getComboBoxGroupByValues();
        this.khoaQuery = "";
        this.nganhQuery = "";
        this.groupBy = "date";
        // Set fromDate to 7 days ago, toDate to today
        LocalDateTime fromDate = LocalDateTime.now().minusDays(6);
        this.dateRange = new DateRange(fromDate, LocalDateTime.now());
        this.chonKhoaCallback = new ChonKhoaCallback();
        this.chonNganhCallback = new ChonNganhCallback();
        initComponent();

        this.lblDateRange.setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
        this.getStat(this.dateRange, this.groupBy, this.khoaQuery, this.nganhQuery);
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    private void initComponent() {
        this.setBackground(backgroundColor);
        this.setLayout(new BorderLayout(10, 10));
        this.setOpaque(false);

        this.pbrFilter = new PanelBorderRadius();
        this.pbrFilter.setPreferredSize(new Dimension(300, 600));
        this.pbrFilter.setLayout(new BorderLayout());

        this.pFilter = new JPanel(new GridLayout(20, 1, 0, 5));
        this.pFilter.setPreferredSize(new Dimension(300, 150));
        this.pFilter.setBackground(Color.WHITE);
        this.pFilter.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.lblDateRange = new JLabel("04/04/2024");
        this.cbDateRange = new JComboBox();
        this.cbDateRange.setModel(new DefaultComboBoxModel<>(this.comboboxDateValues));
        this.cbDateRange.setSelectedIndex(0);

        this.lblGroupBy = new JLabel("Nhóm kết quả theo");
        this.cbGroupBy = new JComboBox();
        this.cbGroupBy.setModel(new DefaultComboBoxModel<>(this.comboboxGroupByValues));
        this.cbGroupBy.setSelectedIndex(0);

        this.lblKhoa = new JLabel("Khoa");
        this.btnChooseKhoa = new JButton("Chọn");
        this.btnChooseKhoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.pKhoa = new JPanel(new BorderLayout());
        this.pKhoa.setBackground(Color.WHITE);
        this.pKhoa.add(this.lblKhoa, BorderLayout.CENTER);
        this.pKhoa.add(this.btnChooseKhoa, BorderLayout.EAST);

        this.txtKhoa = new JTextField("Tất cả");
        this.txtKhoa.setEditable(false);

        this.lblNganh = new JLabel("Ngành");
        this.btnChooseNganh = new JButton("Chọn");
        this.btnChooseNganh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.pNganh = new JPanel(new BorderLayout());
        this.pNganh.setBackground(Color.WHITE);
        this.pNganh.add(this.lblNganh, BorderLayout.CENTER);
        this.pNganh.add(this.btnChooseNganh, BorderLayout.EAST);

        this.txtNganh = new JTextField("Tất cả");
        this.txtNganh.setEditable(false);

        this.btnGetStat = new JButton("Thống kê");
        this.btnGetStat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnViewChart = new JButton("Xem biểu đồ");
        this.btnViewChart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.pButtons = new JPanel(new GridLayout(1, 2));
        this.pButtons.setBackground(Color.WHITE);
        this.pButtons.add(this.btnGetStat);
        this.pButtons.add(this.btnViewChart);

        spMessageOptionChanged = new JScrollPane();
        spMessageOptionChanged.setBackground(this.backgroundColor);
        spMessageOptionChanged.setBorder(null);
        spMessageOptionChanged.setForeground(this.backgroundColor);
        spMessageOptionChanged.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spMessageOptionChanged.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        spMessageOptionChanged.setOpaque(false);
        spMessageOptionChanged.setVisible(false);

        taOptionChanged = new JTextArea();
        taOptionChanged.setEditable(false);
        taOptionChanged.setBackground(this.backgroundColor);
        taOptionChanged.setColumns(20);
        taOptionChanged.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        taOptionChanged.setLineWrap(true);
        taOptionChanged.setRows(3);
        taOptionChanged.setText("Do thống kê theo khoảng thời gian dài, kết quả thống kê mặc định sẽ được nhóm theo tháng.");
        taOptionChanged.setWrapStyleWord(true);
        taOptionChanged.setFocusable(false);
        taOptionChanged.setMargin(new java.awt.Insets(0, 0, 0, 0));
        taOptionChanged.setOpaque(false);
        spMessageOptionChanged.setViewportView(taOptionChanged);

        this.pbrTable = new PanelBorderRadius();
        this.pbrTable.setLayout(new BorderLayout());

        this.pTable = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.table = new JTable();
        this.tableModel = new DefaultTableModel();
        String[] header = new String[]{"Thời gian", "Số lượng"};
        this.tableModel.setColumnIdentifiers(header);
        this.table.setModel(this.tableModel);
//        this.table.setAutoCreateRowSorter(true);
        this.table.setDefaultEditor(Object.class, null);
        this.scrollPane.setViewportView(this.table);

        // column set preferredwidth
        // set focusable false
        this.table.getColumnModel().getColumn(0).setCellRenderer(CustomTableCellRenderer.CENTER);
        this.table.getColumnModel().getColumn(1).setCellRenderer(CustomTableCellRenderer.RIGHT);

        this.table.setFocusable(false);
        this.pFilter.add(this.lblDateRange);
        this.pFilter.add(this.cbDateRange);
        this.pFilter.add(this.lblGroupBy);
        this.pFilter.add(this.cbGroupBy);
        this.pFilter.add(this.pKhoa);
        this.pFilter.add(this.txtKhoa);
        this.pFilter.add(this.pNganh);
        this.pFilter.add(this.txtNganh);
        this.pFilter.add(this.pButtons);
        this.pFilter.add(this.spMessageOptionChanged);
        this.pTable.add(this.scrollPane);
        this.pbrFilter.add(this.pFilter, BorderLayout.CENTER);
        this.pbrTable.add(this.pTable, BorderLayout.CENTER);
        this.add(this.pbrFilter, BorderLayout.WEST);
        this.add(this.pbrTable, BorderLayout.CENTER);

        this.cbDateRange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleComboBoxOptionChanged();
            }
        });

        this.cbGroupBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleComboBoxGroupByChanged();
            }
        });
        this.btnGetStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spMessageOptionChanged.setVisible(false);
                if (checkDiffInDays(dateRange)) {
                    spMessageOptionChanged.setVisible(true);
                }
                getStat(dateRange, groupBy, khoaQuery, nganhQuery);
            }
        });

        this.btnChooseKhoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleOpenDialogChonKhoa();
            }
        });

        this.btnChooseNganh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleOpenDialogChonNganh();
            }
        });
        
        this.btnViewChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleOpenChart();
            }
        });
    }

    private void handleOpenDialogChonKhoa() {
        DialogChoose dialog = new DialogChoose((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Chọn khoa:", this.chonKhoaCallback);
        dialog.setVisible(true);
    }

    private void handleOpenDialogChonNganh() {
        DialogChoose dialog = new DialogChoose((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Chọn ngành:", this.chonNganhCallback);
        dialog.setVisible(true);
    }
    
    private void handleOpenChart() {
        KhuHocTapChart chart = new KhuHocTapChart(this.ttsdBLL, this.list, this.dateRange, this.groupBy, this.khoaQuery, this.nganhQuery);
        chart.setVisible(true);
    }

    private void handleComboBoxOptionChanged() {
        boolean changed = ComboBoxDateValues.handleComboBoxChanged(this, String.valueOf(this.cbDateRange.getSelectedItem()), this.dateRange, this);
        if (changed) {
            setDateRange(this.dateRange);
            this.lblDateRange.setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
        }
    }

    private void handleComboBoxGroupByChanged() {
        spMessageOptionChanged.setVisible(false);
        switch (String.valueOf(cbGroupBy.getSelectedItem())) {
            case ComboBoxDateValues.GROUP_BY_DATE:
                groupBy = "date";
                break;
            case ComboBoxDateValues.GROUP_BY_MONTH:
                groupBy = "month";
                break;
            case ComboBoxDateValues.GROUP_BY_YEAR:
                groupBy = "year";
                break;
            default:
        }
    }

    private boolean checkDiffInDays(DateRange dateRange) {
        boolean modifiedUserOption = false;

        if (dateRange.getFromDate() != null && dateRange.getToDate() != null) {
            long differenceInDays = ChronoUnit.DAYS.between(dateRange.getFromDate(), dateRange.getToDate());
            if (differenceInDays <= ComboBoxDateValues.QUERY_MAX_DATES) {
                return modifiedUserOption;
            }
        }

        switch (String.valueOf(cbGroupBy.getSelectedItem())) {
            case ComboBoxDateValues.GROUP_BY_DATE:
                cbGroupBy.setSelectedIndex(1);
                groupBy = "month";
                modifiedUserOption = true;
                break;
            default:
        }

        return modifiedUserOption;
    }

    private void getStat(DateRange dateRange, String groupBy, String khoa, String nganh) {
        ArrayList<ThongKeKhuHocTap> list = ttsdBLL.thongKeKhuHocTap(dateRange, groupBy, khoa, nganh);
        this.tableModel.setRowCount(0);
        for (ThongKeKhuHocTap i : list) {
            LocalDateTime localDateTime = DateHelper.convertDateObjToLDT(i.getTimeline());
            String timeline = "";
            switch (groupBy) {
                case "date":
                    timeline = localDateTime.format(DateHelper.DATE_FORMATTER);
                    break;
                case "month":
                    timeline = localDateTime.format(DateHelper.MONTH_FORMATTER);
                    break;
                case "year":
                    timeline = localDateTime.format(DateHelper.YEAR_FORMATTER);
                    break;
            }
            Object[] row = {timeline, i.getAmount()};
            this.tableModel.addRow(row);
        }
        this.list = list;
    }

    private class ChonKhoaCallback extends DialogChoose.Callback {

        public ChonKhoaCallback() {
            super();
        }

        @Override
        public ArrayList<String> getList(String query) {
            return tvBLL.getListKhoa(query);
        }

        @Override
        public void handleConfirm(String selectedValue) {
            khoaQuery = selectedValue;
            txtKhoa.setText(khoaQuery.equals("") ? "Tất cả" : khoaQuery);
        }
    }

    private class ChonNganhCallback extends DialogChoose.Callback {

        @Override
        public ArrayList<String> getList(String query) {
            return tvBLL.getListNganh(query);
        }

        @Override
        public void handleConfirm(String selectedValue) {
            nganhQuery = selectedValue;
            txtNganh.setText(nganhQuery.equals("") ? "Tất cả" : nganhQuery);
        }

    }

    @Override
    public void onDateRangeSelected(DateRange dateRange) {
        setDateRange(dateRange);
        this.lblDateRange.setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
    }
}
