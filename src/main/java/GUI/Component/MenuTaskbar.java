/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component;

import GUI.Main;
import GUI.Panel.ThanhVienPanel;
import GUI.Panel.ThietBiPanel;
import GUI.Panel.ThongTinSuDungPanel;
import GUI.Panel.TrangChu;
import GUI.Panel.XuLyPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DELL
 */
public class MenuTaskbar extends JPanel {
    TrangChu trangChu;
    ThanhVienPanel thanhvien;
    ThietBiPanel thietbi;
    ThongTinSuDungPanel thongtinsudung;
    XuLyPanel pnXuLy;
    
    String[][] getSt = {
        {"Trang chủ", "home.svg", "trangchu"},
        {"Thành viên", "product.svg", "thanhvien"},
        {"Thiết bị", "brand.svg", "thietbi"},
        {"Thông tin sử dụng", "area.svg", "thongtinsd"},
        {"Xử lý vi phạm", "import.svg", "xuly"},};

    Main main;
    public itemTaskbar[] listitem;

    JScrollPane scrollPane;

    // TaskbarMenu chia thành 3 phần chính là pnlCenter, pnlTop, pnlBottom
    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(96, 125, 139);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(1, 87, 155);
    Color HowerBackgroundColor = new Color(187, 222, 251);

    public MenuTaskbar(Main main) {
        this.main = main;
        initComponent();
    }

    private void initComponent() {
        listitem = new itemTaskbar[getSt.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new FlowLayout(0, 0, 5));

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        this.add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        pnlBottom = new JPanel();
        pnlBottom.setPreferredSize(new Dimension(250, 50));
        pnlBottom.setBackground(DefaultColor);
        pnlBottom.setLayout(new BorderLayout(0, 0));

        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        pnlBottom.add(bar4, BorderLayout.EAST);

        this.add(pnlBottom, BorderLayout.SOUTH);

        for (int i = 0; i < getSt.length; i++) {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlCenter.add(listitem[i]);
        }

        listitem[0].setBackground(HowerBackgroundColor);
        listitem[0].setForeground(HowerFontColor);
        listitem[0].isSelected = true;

        for (int i = 0; i < getSt.length; i++) {
            listitem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                }
            });
        }

        listitem[0].addMouseListener(new MouseAdapter() { // nut trang chu
            @Override
            public void mousePressed(MouseEvent evt) {
                trangChu = new TrangChu();
                main.setPanel(trangChu);
            }
        });

        listitem[1].addMouseListener(new MouseAdapter() { // nut thanh vien
            @Override
            public void mousePressed(MouseEvent evt) {
                thanhvien = new ThanhVienPanel(main);
                main.setPanel(thanhvien);
            }
        });

        listitem[2].addMouseListener(new MouseAdapter() { // nut thiet bi
            @Override
            public void mousePressed(MouseEvent evt) {
                thietbi = new ThietBiPanel(main);
                main.setPanel(thietbi);
            }
        });

        listitem[3].addMouseListener(new MouseAdapter() { // nut thong tin su dung
            @Override
            public void mousePressed(MouseEvent evt) {
                thongtinsudung = new ThongTinSuDungPanel(main);
                main.setPanel(thongtinsudung);
            }
        });
        
        listitem[4].addMouseListener(new MouseAdapter() { // nut vi pham
            @Override
            public void mousePressed(MouseEvent evt) {
                pnXuLy = new XuLyPanel(main);
                main.setPanel(pnXuLy);
            }
        });
        pnlCenter.revalidate();
        pnlCenter.repaint();
    }

    public void pnlMenuTaskbarMousePress(MouseEvent evt) {

        for (int i = 0; i < getSt.length; i++) {
            if (evt.getSource() == listitem[i]) {
                listitem[i].isSelected = true;
                listitem[i].setBackground(HowerBackgroundColor);
                listitem[i].setForeground(HowerFontColor);
            } else {
                listitem[i].isSelected = false;
                listitem[i].setBackground(DefaultColor);
                listitem[i].setForeground(FontColor);
            }
        }
    }

//    private void displaySVGImage(String svgFilePath, JPanel panelToDisplay) {
//        try {
//            // Tạo một đối tượng SVGCanvas để hiển thị hình ảnh SVG
//            JSVGCanvas canvas = new JSVGCanvas();
//
//            // Đọc tệp SVG từ đường dẫn
//            File svgFile = new File(svgFilePath);
//            FileInputStream fileInputStream = new FileInputStream(svgFile);
//
//            // Tạo TranscoderInput từ FileInputStream
//            TranscoderInput transcoderInput = new TranscoderInput(fileInputStream);
//
//            // Thiết lập SVGCanvas để hiển thị hình ảnh SVG
//            canvas.setURI(svgFile.toURI().toString());
//
//            // Thêm SVGCanvas vào panelToDisplay
//            panelToDisplay.add(canvas);
//            panelToDisplay.revalidate();
//            panelToDisplay.repaint();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
