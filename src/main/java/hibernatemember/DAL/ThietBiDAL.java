/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import BLL.ThongTinSuDungBLL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author DELL
 */
public class ThietBiDAL {

    private ThongTinSuDungBLL thongtinBLL = new ThongTinSuDungBLL();
    Session session;

    public ThietBiDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }

    public List loadThietBi() {
        List<ThietBi> thietbi;
        session.beginTransaction();
        thietbi = session.createQuery("FROM ThietBi", ThietBi.class).list();
        session.getTransaction().commit();
        return thietbi;
    }

    public ThietBi getThietBi(int thietbiID) {
        session = HibernateUtils.getSessionFactory().openSession();
        ThietBi c = session.get(ThietBi.class, thietbiID);
        session.close();
        return c;
    }

    public void addThietBi(ThietBi c) {

        session.save(c);

    }

    public void updateThietBi(ThietBi c) {
        session.update(c);

    }

    public void deleteThietBi(ThietBi c) {
        session.delete(c);
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
            for(int ma : maThietBiList) {
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
}
