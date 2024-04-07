package GUI.Panel;

import BLL.ThanhVienBLL;
import BLL.ThongTinSuDungBLL;
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
    private JTabbedPane tabbedPane;
    private JPanel tkKhuHocTap;
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

        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setOpaque(false);
        this.tabbedPane.addTab("Khu học tập", this.tkKhuHocTap);

        this.add(tabbedPane);
    }
}
