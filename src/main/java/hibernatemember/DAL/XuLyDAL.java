/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import POJO.DateRange;
import helper.DateHelper;
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
public class XuLyDAL {

    Session session;

    public XuLyDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }

    public ArrayList getHinhThucXuLy() {
        ArrayList<String> arrHinhThucXL = new ArrayList<>(Arrays.asList(
                "Khóa thẻ 1 tháng",
                "Khóa thẻ 2 tháng",
                "Khóa thẻ vĩnh viễn",
                "Bồi thường",
                "Khóa thẻ 3 tháng và bồi thường"
        ));
        return arrHinhThucXL;
    }

    public ArrayList loadXuLy() {
        session = HibernateUtils.getSessionFactory().openSession();
        ArrayList<XuLy> listXuLy = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            listXuLy = (ArrayList<XuLy>) session.createQuery("FROM XuLy", XuLy.class).list();
            for (XuLy xuLy : listXuLy) {
                if (xuLy.getSoTien() == null) {
                    xuLy.setSoTien(0);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi tải dữ liệu vi phạm: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listXuLy;
    }

    public XuLy getXuLy(int xuLyID) {
        session = HibernateUtils.getSessionFactory().openSession();
        XuLy xuLy = session.get(XuLy.class, xuLyID);
        session.close();
        return xuLy;
    }

    public boolean addXuLy(XuLy xuLy) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            tx = session.beginTransaction();
            session.save(xuLy);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi thêm vi phạm: ");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean updateXuLy(XuLy xuLy) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            tx = session.beginTransaction();
            session.merge(xuLy);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi cập nhật vi phạm: ");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean deleteXuLy(XuLy xuLy) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(xuLy);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi xóa vi phạm: ");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public ArrayList<XuLy> getStatXuLy(int state, DateRange dateRange, String memberName) {
        ArrayList<XuLy> list = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);
        String toDate = dateRange.getToDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("""
                            SELECT x
                            FROM XuLy x
                                JOIN x.thanhVien tv
                            WHERE TrangThaiXL = :state AND NgayXL BETWEEN DATE(:fromDate) AND DATE(:toDate) AND HoTen LIKE :memberName
                            ORDER BY NgayXL DESC
                            """);
        session.beginTransaction();
        Query query = session.createQuery(queryBuilder.toString(), XuLy.class);
        query.setParameter("state", state);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("memberName", "%" + memberName + "%");
        list = (ArrayList<XuLy>) query.list();
        session.getTransaction().commit();
        return list;
    }
}
