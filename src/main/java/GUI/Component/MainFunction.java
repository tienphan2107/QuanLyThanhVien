/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import java.awt.Color;
import java.util.HashMap;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

/**
 *
 * @author DELL
 */
public class MainFunction extends JToolBar {

    public ButtonToolBar btnAdd, btnDelete, btnEdit, btnDetail, btnNhapExcel, btnXuatExcel, btnHuyPhieu;
    public JSeparator separator1;
    public HashMap<String, ButtonToolBar> btn = new HashMap<>();

    public MainFunction(String[] listBtn) {
        initData();
        initComponent(listBtn);
    }

    public void initData() {
        btn.put("create", new ButtonToolBar("THÊM", "add.svg", "create"));
        btn.put("delete", new ButtonToolBar("XÓA", "delete.svg", "delete"));
        btn.put("update", new ButtonToolBar("SỬA", "edit.svg", "update"));
        btn.put("cancel", new ButtonToolBar("HUỶ PHIẾU", "cancel.svg", "delete"));
        btn.put("detail", new ButtonToolBar("CHI TIẾT", "detail.svg", "view"));
        btn.put("import", new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create"));
        btn.put("export", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));
        btn.put("phone", new ButtonToolBar("XEM DS", "phone.svg", "view"));
        btn.put("vaokhutuhoc", new ButtonToolBar("VÀO KHU HỌC TẬP", "add.svg", "create"));
        btn.put("muon", new ButtonToolBar("MƯỢN", "add.svg", "create"));
        btn.put("tra", new ButtonToolBar("TRẢ", "phone.svg", "create"));
        btn.put("datcho", new ButtonToolBar("ĐẶT CHỖ", "phone.svg", "create"));

    }

    private void initComponent(String[] listBtn) {
        this.setBackground(Color.WHITE);
        this.setRollover(true);
        initData();
        for (String btnn : listBtn) {
            this.add(btn.get(btnn));
            btn.get(btnn).setEnabled(true);
        }
    }
}
