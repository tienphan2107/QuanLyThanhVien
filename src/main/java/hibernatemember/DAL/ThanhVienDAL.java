/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import static org.apache.commons.math3.geometry.VectorFormat.DEFAULT_PREFIX;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author DELL
 */
public class ThanhVienDAL {

    Session session;

    public ThanhVienDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }

    public ArrayList loadThanhVien() {
        session = HibernateUtils.getSessionFactory().openSession();
        ArrayList<ThanhVien> listThanhvien = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            listThanhvien = (ArrayList) session.createQuery("FROM ThanhVien", ThanhVien.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi tải danh sách thành viên: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listThanhvien;
    }

    public ThanhVien getThanhVien(int thanhvienID) {
        session = HibernateUtils.getSessionFactory().openSession();
        ThanhVien c = session.get(ThanhVien.class, thanhvienID);
        session.close();
        return c;
    }

    public boolean addThanhVien(ThanhVien c) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            tx = session.beginTransaction();
            session.save(c);
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

    public boolean updateThanhVien(ThanhVien c) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            tx = session.beginTransaction();
            session.merge(c);
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

    public boolean deleteThanhVien(ThanhVien c) {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(c);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi xóa vi phạm: ");
            e.printStackTrace();
            return false;
        }finally{
            session.close();
        }
    }

    public String createMaTV() {
        // Lấy số cuối của năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;

        // Tạo random từ 1 đến 55
        Random random = new Random();
        int randomNum = random.nextInt(55) + 1;

        // Lấy số lớn nhất có 6 số đầu từ cơ sở dữ liệu
        String soDau = "11" + currentYear + randomNum;
        int maxSequenceNumber = getMaxSequenceNumber(soDau);

        // Tạo mã thành viên
        String maTV;
        if (maxSequenceNumber != 0) {
            // Tăng dần 4 số cuối lên
            int nextSequenceNumber = maxSequenceNumber + 1;
            maTV = nextSequenceNumber + "";
        } else {
            // Tạo 6 số đầu theo quy chuẩn và bắt đầu 4 số cuối là 0001
            maTV = String.format("%02d%02d%02d%04d", 11, currentYear, randomNum, 0001);
        }

        return maTV;
    }

    private int getMaxSequenceNumber(String sauSoDau) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery(
                    "SELECT MAX(MaTV) FROM ThanhVien "
                    + "WHERE MaTV LIKE :pattern"
            );
            int pattern = Integer.parseInt(sauSoDau);
            query.setParameter("pattern", pattern);
            Integer maxMaTV = (Integer) query.uniqueResult();
            session.getTransaction().commit();
            return maxMaTV != null ? maxMaTV : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<String> getListKhoa(String query) {
        session.beginTransaction();
        Query q = session.createNativeQuery("SELECT DISTINCT Khoa FROM thanhvien WHERE Khoa LIKE :khoa");
        q.setParameter("khoa", "%" + query + "%");
        ArrayList<String> result = (ArrayList) q.list();
        session.getTransaction().commit();
        return result;
    }

    public ArrayList<String> getListNganh(String query) {
        session.beginTransaction();
        Query q = session.createNativeQuery("SELECT DISTINCT Nganh FROM thanhvien WHERE Nganh LIKE :nganh");
        q.setParameter("nganh", "%" + query + "%");
        ArrayList<String> result = (ArrayList) q.list();
        session.getTransaction().commit();
        return result;
    }

//    public static void main(String args[])
//    {
//        ThanhVienDAL dal = new ThanhVienDAL();
//        ThanhVien obj = dal.getMember(1);
////        System.out.println(obj.getMemberName());
//        
//        
////        for (Iterator iterator = list.iterator(); iterator.hasNext();){
////             Vegetable v = (Vegetable) iterator.next(); 
////             System.out.print("ID: " + v.getVegetableID()); 
////             System.out.print("Name: " + v.getVegetableName()); 
////             
////          }
//        
//    
//    }
}
