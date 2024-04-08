package GUI.Panel;

import BLL.ThongTinSuDungBLL;
import GUI.Component.InputForm;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import POJO.DateRange;
import helper.ComboBoxDateValues;
import helper.CustomTableCellRenderer;
import helper.DateHelper;
import helper.SelectDateCallback;
import hibernatemember.DAL.ThongTinSuDung;
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

public class ThongKeThietBiPanel extends JPanel implements SelectDateCallback {

    private final ThongTinSuDungBLL ttsdBLL;
    private final String[] comboboxDateValues;
    private final Color backgroundColor = new Color(240, 247, 250);
    private PanelBorderRadius pbrFilter, pbrTable;
    private JPanel pFilter, pTable;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private SelectForm selectType, selectDateRange;
    private InputForm inputDeviceName;
    private DateRange dateRange;
    private String query;
    private boolean isTGTraNull;

    public ThongKeThietBiPanel(ThongTinSuDungBLL ttsdBLL, String[] comboboxDateValues) {
        this.ttsdBLL = ttsdBLL;
        this.comboboxDateValues = comboboxDateValues;
        this.query = "";
        this.isTGTraNull = false;
        // Set fromDate to 7 days ago, toDate to today
        LocalDateTime fromDate = LocalDateTime.now().minusDays(6);
        this.dateRange = new DateRange(fromDate, LocalDateTime.now());
        initComponent();
        this.selectDateRange.getLblTitle().setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
        this.getStat(this.dateRange, this.query, this.isTGTraNull);
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

        this.selectType = new SelectForm("Tình trạng mượn", new String[]{"Đã mượn", "Đang được mượn"});
        this.selectDateRange = new SelectForm("dd/mm/yyyy - dd/mm/yyyy", this.comboboxDateValues);
        this.inputDeviceName = new InputForm("Tên thiết bị");

        this.pFilter.add(this.selectType);
        this.pFilter.add(this.selectDateRange);
        this.pFilter.add(this.inputDeviceName);

        this.pbrTable = new PanelBorderRadius();
        this.pbrTable.setLayout(new BorderLayout());

        this.pTable = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.table = new JTable();
        this.tableModel = new DefaultTableModel();
        String[] header = new String[]{"Mã TB", "Tên thiết bị", "Người mượn", "Thời gian mượn", "Thời gian trả"};
        this.tableModel.setColumnIdentifiers(header);
        this.table.setModel(this.tableModel);
//        this.table.setAutoCreateRowSorter(true);
        this.table.setDefaultEditor(Object.class, null);
        this.table.setFocusable(false);

        this.scrollPane.setViewportView(this.table);

        this.table.getColumnModel().getColumn(0).setPreferredWidth(24);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(160);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(160);
        this.table.getColumnModel().getColumn(3).setCellRenderer(CustomTableCellRenderer.CENTER);
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

        this.inputDeviceName.getTxtForm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                handleInputChanged();
            }
        });
    }

    private void handleComboBoxTypeChanged() {
        int selectedIndex = this.selectType.getSelectedIndex();
        boolean isTGTraNull = selectedIndex == 1;
        if (this.isTGTraNull == isTGTraNull) {
            return;
        }
        this.isTGTraNull = isTGTraNull;
        getStat(this.dateRange, this.query, this.isTGTraNull);
    }

    private void handleComboBoxOptionChanged() {
        boolean changed = ComboBoxDateValues.handleComboBoxChanged(this, String.valueOf(this.selectDateRange.getCbb().getSelectedItem()), this.dateRange, this);
        if (changed) {
            this.selectDateRange.getLblTitle().setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
            getStat(this.dateRange, this.query, this.isTGTraNull);
        }
    }

    private void handleInputChanged() {
        if (inputDeviceName.getText().trim().toLowerCase().equals(query.toLowerCase())) {
            return;
        }
        this.query = inputDeviceName.getText().trim();
        getStat(this.dateRange, this.query, this.isTGTraNull);
    }

    private void getStat(DateRange dateRange, String query, boolean isTGTraNull) {
        ArrayList<ThongTinSuDung> list = ttsdBLL.getStatTTSD(dateRange, query, isTGTraNull);
        this.tableModel.setRowCount(0);
        for (ThongTinSuDung i : list) {
            LocalDateTime ldtTGMuon = DateHelper.convertDateObjToLDT(i.getTGMuon());
            String strTGMuon = ldtTGMuon.format(DateHelper.DATE_TIME_FORMATTER);
            LocalDateTime ldtTGTra = DateHelper.convertDateObjToLDT(i.getTGTra());
            String strTGTra = ldtTGTra == null ? "" : ldtTGTra.format(DateHelper.DATE_TIME_FORMATTER);
            Object[] row = {i.getThietBi().getMaTB(), i.getThietBi().getTenTB(), i.getThanhVien().getHoTen(), strTGMuon, strTGTra};
            this.tableModel.addRow(row);
        }
    }

    @Override
    public void onDateRangeSelected(DateRange dateRange) {
        this.dateRange = dateRange;
        this.selectDateRange.getLblTitle().setText(DateHelper.dateRangeToString(this.dateRange, DateHelper.DATE_FORMATTER, " - "));
        getStat(this.dateRange, this.query, this.isTGTraNull);
    }
}
