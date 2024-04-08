/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

/**
 *
 * @author DELL
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Map;
import org.hibernate.query.Query;

public class ThietBiDAL {

    public List<ThietBi> loadThietBi() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<ThietBi> thietbi = session.createQuery("FROM ThietBi", ThietBi.class).list();
            session.getTransaction().commit();
            return thietbi;
        } catch (Exception e) {
            return null;
        }
    }

    public ThietBi getThietBi(int thietbiID) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(ThietBi.class, thietbiID);
        } catch (Exception e) {
            return null;
        }
    }

    public void addThietBi(ThietBi c) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(c);
            transaction.commit();
        } catch (Exception e) {
        }
    }

    public void updateThietBi(int maTB, String tenTB, String moTa) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            ThietBi tbToUpdate = session.get(ThietBi.class, maTB);
            tbToUpdate.setTenTB(tenTB);
            tbToUpdate.setMoTaTB(moTa);
            session.update(tbToUpdate);

            transaction.commit();
        } catch (Exception e) {
        }
    }

    public void deleteThietBi(int maTB) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<ThietBi> query = session.createQuery("FROM ThietBi WHERE maTB = :maTB", ThietBi.class);
            query.setParameter("maTB", maTB);
            ThietBi thietBi = query.uniqueResult();
            if (thietBi != null) {
                session.delete(thietBi);
            }
            transaction.commit();
        } catch (Exception e) {
        }
    }

    public int getAutoIncrement() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Number result = (Number) session.createQuery("SELECT MAX(id) FROM ThietBi").uniqueResult();
            return (result != null) ? result.intValue() + 1 : 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public String[] getThietBiStrings() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<ThietBi> thietbiList = session.createQuery("FROM ThietBi", ThietBi.class).list();
            session.getTransaction().commit();

            String[] result = new String[thietbiList.size()];
            for (int i = 0; i < thietbiList.size(); i++) {
                ThietBi tb = thietbiList.get(i);
                result[i] = tb.getMaTB() + " - " + tb.getTenTB() + " - " + tb.getMoTaTB();
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getTenThietBi() {
        Map<String, Integer> demTenTB = new HashMap<>();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<ThietBi> thietbiList = session.createQuery("FROM ThietBi", ThietBi.class).list();
            session.getTransaction().commit();

            for (ThietBi tb : thietbiList) {
                String tenTB = tb.getTenTB();
                demTenTB.put(tenTB, demTenTB.getOrDefault(tenTB, 0) + 1);
            }

            List<String> resultList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : demTenTB.entrySet()) {
                resultList.add(entry.getKey() + " - Số lượng: " + entry.getValue());
            }

            return resultList.toArray(String[]::new);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteByTenTB(String tenTB) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query deleteQuery = session.createQuery("DELETE FROM ThietBi WHERE TenTB = :tenTB");
            deleteQuery.setParameter("tenTB", tenTB);

            int affectedRows = deleteQuery.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
