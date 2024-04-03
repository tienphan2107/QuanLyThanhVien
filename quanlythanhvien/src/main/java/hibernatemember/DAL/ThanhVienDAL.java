/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        ArrayList<ThanhVien> thanhvien;
        session.beginTransaction();
        thanhvien = (ArrayList) session.createQuery("FROM ThanhVien", ThanhVien.class).list();
        session.getTransaction().commit();
        return thanhvien;
    }

    public ThanhVien getThanhVien(int thanhvienID) {
        ThanhVien c = session.get(ThanhVien.class, thanhvienID);
        return c;
    }

    public void addThanhVien(ThanhVien c) {
        Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();
    }

    public void updateThanhVien(ThanhVien c) {
        Transaction tx = session.beginTransaction();
        session.merge(c);
        tx.commit();
    }

    public void deleteThanhVien(ThanhVien c) {
        Transaction tx = session.beginTransaction();
        session.delete(c);
        tx.commit();

    }

    public int getAutoIncrement() {
        Number maxIdResult = (Number) session.createQuery("SELECT MAX(id) FROM ThanhVien").uniqueResult();
        int maxId = (maxIdResult != null) ? maxIdResult.intValue() : 0;

        // Kiểm tra xem mã thành viên lớn nhất đã vượt quá giới hạn int hay không
        if (maxId >= Integer.MAX_VALUE) {
            // Nếu đã vượt quá, quay lại kiểm tra từ mã 1 đến khi không còn trùng lặp
            int newId = 1;
            while (true) {
                Number countResult = (Number) session.createQuery("SELECT COUNT(*) FROM ThanhVien WHERE id = :newId")
                        .setParameter("newId", newId)
                        .uniqueResult();
                if (countResult != null && countResult.intValue() == 0) {
                    break; // Không còn trùng lặp, thoát vòng lặp
                }
                newId++;
            }
            return newId;
        } else {
            // Nếu chưa vượt quá, tiếp tục tăng mã thành viên lên 1
            int newId = maxId + 1;
            // Kiểm tra xem mã mới đã tồn tại chưa, nếu có thì tăng giá trị lên 1 cho đến khi không còn trùng lặp
            while (true) {
                Number countResult = (Number) session.createQuery("SELECT COUNT(*) FROM ThanhVien WHERE id = :newId")
                        .setParameter("newId", newId)
                        .uniqueResult();
                if (countResult != null && countResult.intValue() == 0) {
                    break; // Không còn trùng lặp, thoát vòng lặp
                }
                newId++;
            }
            return newId;
        }
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
