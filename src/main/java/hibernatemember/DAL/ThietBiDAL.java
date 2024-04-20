/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import BLL.ThongTinSuDungBLL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL
 */
public class ThietBiDAL {

    private ThongTinSuDungBLL thongtinBLL = new ThongTinSuDungBLL();
    Session session;

//    public ThietBiDAL() {
//        session = HibernateUtils.getSessionFactory().openSession();
//    }
    public List loadThietBi() {
        session = HibernateUtils.getSessionFactory().openSession();
        ArrayList<ThietBi> listThietBi = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            listThietBi = (ArrayList) session.createQuery("FROM ThietBi", ThietBi.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi tải danh sách thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listThietBi;
    }
    public List<ThietBi> loadThietBiFilter(String keyword) {
    session = HibernateUtils.getSessionFactory().openSession();
    ArrayList<ThietBi> listThietBi = new ArrayList<>();
    Transaction tx = null;
    try {
        tx = session.beginTransaction();
        // Tạo câu truy vấn linh hoạt sử dụng LIKE để tìm kiếm theo các cột maTB, tenTB, motaTB
        String queryString = "FROM ThietBi WHERE maTB LIKE :keyword OR tenTB LIKE :keyword OR motaTB LIKE :keyword";
        Query query = session.createQuery(queryString, ThietBi.class);
        query.setParameter("keyword", "%" + keyword + "%");
        
        // Thực hiện truy vấn và lấy danh sách thiết bị
        listThietBi = (ArrayList<ThietBi>) query.list();
        
        tx.commit();
    } catch (HibernateException e) {
        if (tx != null) {
            tx.rollback();
        }
        System.out.print("Lỗi khi tải danh sách thiết bị: ");
        e.printStackTrace();
    } finally {
        session.close();
    }
    return listThietBi;
}


    public ThietBi getThietBi(int thietbiID) {
        session = HibernateUtils.getSessionFactory().openSession();
        ThietBi c = session.get(ThietBi.class, thietbiID);
        session.close();
        return c;
    }

    public void addThietBi(ThietBi c) {

        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi thêm thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void updateThietBi(ThietBi c) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi cập nhật thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void deleteThietBi(int maTB) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Query<ThietBi> query = session.createQuery("FROM ThietBi WHERE maTB = :maTB", ThietBi.class);
            query.setParameter("maTB", maTB);
            ThietBi thietBi = query.uniqueResult();
            if (thietBi != null) {
                session.delete(thietBi);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi xóa thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList getDanhSachLoaiThietBi() {
        ArrayList<String> arrLoaiThietBi = new ArrayList<>(Arrays.asList(
                "Micro",
                "Máy chiếu",
                "Máy ảnh",
                "Cassette",
                "Tivi",
                "Quạt đứng"
        ));
        return arrLoaiThietBi;
    }

    public String[] getListTenTB() {
        String[] tenTBArray = new String[0];
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String queryString = "SELECT tb.TenTB FROM ThietBi tb";
            Query<String> query = session.createQuery(queryString, String.class);
            List<String> tenTBList = query.list();
            tenTBArray = new String[tenTBList.size()];
            for (int i = 0; i < tenTBList.size(); i++) {
                tenTBArray[i] = tenTBList.get(i);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi lấy danh sách tên thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tenTBArray;
    }

    public int getMaThietBi(String tenThietBi) {
        int maThietBi = -1; // Mã thiết bị mặc định nếu không tìm thấy
        List<Integer> maThietBiList = new ArrayList<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Integer> query = session.createQuery("SELECT tb.MaTB FROM ThietBi tb WHERE tb.TenTB = :tenThietBi", Integer.class);
            query.setParameter("tenThietBi", tenThietBi);
            List<Integer> results = query.list();
            if (results != null) {
                maThietBiList.addAll(results);
            }
            for (int ma : maThietBiList) {
                if (thongtinBLL.checkMaTBExists(ma) == false) {
                    maThietBi = ma;
                    break;
                }
            }
//            Integer result = query.uniqueResult();
//            maThietBi = result != null ? result : -1;
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.print("Lỗi khi lấy mã thiết bị: ");
            e.printStackTrace();
        }
        return maThietBi;
    }

    public String getTenThietBi(int maThietBi) {
        String tenThietBi = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            // Thực hiện truy vấn để lấy tên thiết bị dựa trên mã thiết bị
            String query = "SELECT tb.TenTB FROM ThietBi tb WHERE tb.MaTB = :maThietBi";
            Query<String> nameQuery = session.createQuery(query, String.class);
            nameQuery.setParameter("maThietBi", maThietBi);
            tenThietBi = nameQuery.getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return tenThietBi;
    }

    public String[] getThietBiStrings() {
        Transaction tx = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            List<ThietBi> thietbiList = session.createQuery("FROM ThietBi", ThietBi.class).list();
            tx.commit();
            String[] result = new String[thietbiList.size()];
            for (int i = 0; i < thietbiList.size(); i++) {
                ThietBi tb = thietbiList.get(i);
                result[i] = tb.getMaTB() + " - " + tb.getTenTB() + " - " + tb.getMoTaTB();
            }
            return result;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Lỗi khi lấy danh sách thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void deleteThietBiByStartingNumber(String startingNumber) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String queryString = "FROM ThietBi WHERE cast(maTB as string) LIKE :startingNumber";
            Query<ThietBi> query = session.createQuery(queryString, ThietBi.class);
            query.setParameter("startingNumber", startingNumber + "%");
            List<ThietBi> thietBiList = query.list();
            for (ThietBi thietBi : thietBiList) {
                session.delete(thietBi);
            }
            tx.commit();
        } catch (HibernateException e) {
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    private final Map<String, Integer> deviceTypeMap = new HashMap<>();
    private final Map<String, Integer> deviceCounterMap = new HashMap<>();

    public ThietBiDAL() {
        // Khởi tạo bản đồ loại thiết bị
        deviceTypeMap.put("micro", 1);
        deviceTypeMap.put("máy chiếu", 2);
        deviceTypeMap.put("máy ảnh", 3);
        deviceTypeMap.put("cassette", 4);
        deviceTypeMap.put("tivi", 5);
        deviceTypeMap.put("quạt đứng", 6);

        // Khởi tạo số thứ tự từ cơ sở dữ liệu
        List<ThietBi> existingDevices = loadThietBi();
        if (existingDevices != null) {
            for (ThietBi device : existingDevices) {
                String deviceTypeKey = getDeviceTypeKey(device.getTenTB());
                String maTBString = String.valueOf(device.getMaTB());
                int deviceNumber = Integer.parseInt(maTBString.substring(5));
                deviceCounterMap.put(deviceTypeKey, Math.max(deviceCounterMap.getOrDefault(deviceTypeKey, 0), deviceNumber));
            }
        }
    }

    private String getDeviceTypeKey(String deviceName) {
        deviceName = deviceName.toLowerCase();
        if (deviceName.contains("micro")) {
            return "micro";
        } else if (deviceName.contains("máy chiếu")) {
            return "máy chiếu";
        } else if (deviceName.contains("máy ảnh")) {
            return "máy ảnh";
        } else if (deviceName.contains("cassette")) {
            return "cassette";
        } else if (deviceName.contains("tivi")) {
            return "tivi";
        } else if (deviceName.contains("quạt đứng")) {
            return "quạt đứng";
        }
        return null;
    }

    public String generateDeviceCode(String deviceName) {
        String deviceTypeKey = getDeviceTypeKey(deviceName);
        if (deviceTypeKey == null) {
            return "Invalid device name";
        }
        int deviceType = deviceTypeMap.get(deviceTypeKey);
        int currentYear = java.time.Year.now().getValue();
        int count = deviceCounterMap.getOrDefault(deviceTypeKey, 0) + 1;
        deviceCounterMap.put(deviceTypeKey, count);
        return deviceType + String.format("%04d", currentYear) + count;
    }

    public void importFromExcel(File excelFile) {
        Transaction tx = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            FileInputStream fis = new FileInputStream(excelFile);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();

            // Bỏ qua tiêu đề nếu có
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                ThietBi thietBi = new ThietBi();
                thietBi.setMaTB((int) row.getCell(0).getNumericCellValue());
                thietBi.setTenTB(row.getCell(1).getStringCellValue());
                thietBi.setMoTaTB(row.getCell(2).getStringCellValue());

                // Thêm vào cơ sở dữ liệu
                tx = session.beginTransaction();
                session.save(thietBi);
                tx.commit();
            }
            fis.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi nhập thiết bị từ file excel: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void exportToExcel(File file, JTable tableThietBi) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
