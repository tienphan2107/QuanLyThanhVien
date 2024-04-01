/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author DELL
 */
public class ThanhVienDAL {
    Session session;

    public ThanhVienDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }
    
    public List loadThanhVien() {
        List<ThanhVien> thanhvien;
        session.beginTransaction();
        thanhvien = session.createQuery("FROM ThanhVien", ThanhVien.class).list();
        session.getTransaction().commit();
        return thanhvien;
    }
    public ThanhVien getThanhVien(int thanhvienID)
    {
        ThanhVien c = session.get(ThanhVien.class, thanhvienID);
        return c;
    }
    public void addThanhVien(ThanhVien c)
    {
       
        session.save(c);
        
    }
    public void updateThanhVien(ThanhVien c)
    {
        session.update(c);
        
    }
    public void deleteThanhVien(ThanhVien c)
    {
        session.delete(c);
    }
    
    public int getAutoIncrement() {
        Number result = (Number) session.createQuery("SELECT MAX(id) FROM ThanhVien").uniqueResult();
        return (result != null) ? result.intValue() + 1 : 1;
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
