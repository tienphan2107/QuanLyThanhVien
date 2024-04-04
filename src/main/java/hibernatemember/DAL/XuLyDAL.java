/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author DELL
 */
public class XuLyDAL {

    Session session;

    public XuLyDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }

    public ArrayList loadXuLy() {
        ArrayList<XuLy> listXuLy = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            listXuLy = (ArrayList<XuLy>) session.createQuery("FROM XuLy").list();
            for (XuLy xuLy : listXuLy){
                if(xuLy.getSoTien() == null){
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
        }
        return listXuLy;
    }

    public XuLy getXuLy(int xuLyID) {
        XuLy xuLy = session.get(XuLy.class, xuLyID);
        return xuLy;
    }

    public boolean addXuLy(XuLy xuLy) {
        Transaction tx = null;
        try {
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
        }
    }

    public boolean updateXuLy(XuLy xuLy) {
        Transaction tx = null;
        try {
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
        }
    }

    public boolean deleteXuLy(XuLy xuLy) {
        Transaction tx = null;
        try {
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
        }
    }
}
