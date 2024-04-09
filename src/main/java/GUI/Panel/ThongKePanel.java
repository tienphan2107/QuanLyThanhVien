package GUI.Panel;

import BLL.ThanhVienBLL;
import BLL.ThongTinSuDungBLL;
import BLL.XuLyBLL;
import GUI.Main;
import helper.ComboBoxDateValues;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ThongKePanel extends JPanel {

    private final Main main;
    private final Color backgroundColor = new Color(240, 247, 250);
    private final ThanhVienBLL tvBLL = new ThanhVienBLL();
    private final ThongTinSuDungBLL ttsdBLL = new ThongTinSuDungBLL();
    private final XuLyBLL xlBLL = new XuLyBLL();
    private JTabbedPane tabbedPane;
    private JPanel tkKhuHocTap, tkThietBi, tkViPham;
    private String[] comboboxDateValues;

    public ThongKePanel(Main main) {
        this.main = main;
        this.comboboxDateValues = ComboBoxDateValues.getComboBoxDateValues();
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new GridLayout(1, 1));
        this.setBackground(backgroundColor);

        this.tkKhuHocTap = new ThongKeKhuHocTapPanel(this.tvBLL, this.ttsdBLL, this.comboboxDateValues);
        this.tkThietBi = new ThongKeThietBiPanel(this.ttsdBLL, this.comboboxDateValues);
        this.tkViPham = new ThongKeViPhamPanel(this.xlBLL, this.comboboxDateValues);

        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setOpaque(false);
        this.tabbedPane.addTab("Khu học tập", this.tkKhuHocTap);
        this.tabbedPane.addTab("Thiết bị", this.tkThietBi);
        this.tabbedPane.addTab("Vi phạm", this.tkViPham);

        this.add(tabbedPane);
    }
}
