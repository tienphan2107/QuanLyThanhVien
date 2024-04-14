/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author DELL
 */
public class ThietBiDAL {

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
        ThietBi c = session.get(ThietBi.class, thietbiID);
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
}
