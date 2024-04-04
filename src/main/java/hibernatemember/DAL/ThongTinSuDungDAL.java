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
public class ThongTinSuDungDAL {
    Session session;

    public ThongTinSuDungDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }
    
    public List loadThongTinSuDung() {
        List<ThongTinSuDung> thongtinsudung;
        session.beginTransaction();
        thongtinsudung = session.createQuery("FROM ThongTinSuDung", ThongTinSuDung.class).list();
        session.getTransaction().commit();
        return thongtinsudung;
    }
    public ThongTinSuDung getThongTinSuDung(int MaTT)
    {
        ThongTinSuDung c = session.get(ThongTinSuDung.class, MaTT);
        return c;
    }
    public void addThongTinSuDung(ThongTinSuDung c)
    {
       
        session.save(c);
        
    }
    public void updateThongTinSuDung(ThongTinSuDung c)
    {
        session.update(c);
        
    }
    public void deleteThongTinSuDung(ThongTinSuDung c)
    {
        session.delete(c);
    }
}
