/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import POJO.DateRange;
import POJO.ThongKeKhuHocTap;
import helper.DateHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

/**
 *
 * @author DELL
 */
public class ThongTinSuDungDAL {

    private ThietBi thietBi = new ThietBi();
    Session session;

    public ThongTinSuDungDAL() {
        session = HibernateUtils.getSessionFactory().openSession();
    }

    public ArrayList loadThongTinSuDung() throws ParseException {
        session = HibernateUtils.getSessionFactory().openSession();
        ArrayList<ThongTinSuDung> listThongTin = new ArrayList<>();
        Transaction tx = null;
        String dateString = "0000-00-00 00:00:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(dateString);

        try {
            tx = session.beginTransaction();
            listThongTin = (ArrayList<ThongTinSuDung>) session.createQuery("FROM ThongTinSuDung", ThongTinSuDung.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi tải dữ liệu tham gia: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return listThongTin;
    }

    public int getMaxId() {
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("select max(MaTT) from ThongTinSuDung");
            Integer maxId = (Integer) query.uniqueResult();
            tx.commit();
            session.close();
            return maxId != null ? maxId : 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public boolean addThongTinSuDung(ThongTinSuDung c) {
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
            System.out.print("Lỗi khi tham gia khu học tập: ");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

//    public ThongTinSuDung getThongTinSuDung(int MaTT) {
//        session = HibernateUtils.getSessionFactory().openSession();
//        ThongTinSuDung c = session.get(ThongTinSuDung.class, MaTT);
//        session.close();
//        return c;
//    }

    public boolean updateThongTinSuDung(ThongTinSuDung c) {
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
            System.out.print("Lỗi khi cho mượn thiết bị: ");
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }

    }

//    public boolean updateThongTinSuDung(ThongTinSuDung c) {
//        Transaction tx = null;
//        try {
//            session = HibernateUtils.getSessionFactory().openSession();
//            tx = session.beginTransaction();
//            session.update(c);
//            tx.commit();
//            return true;
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            System.out.print("Lỗi khi cập nhật thông tin sử dụng: ");
//            e.printStackTrace();
//            return false;
//        } finally {
//            session.close();
//        }
//    }
    public void deleteThongTinSuDung(ThongTinSuDung c) {
        session.delete(c);
    }

    public String[] getListMaTB() {
        String[] maTBArray = new String[0];
        Transaction tx = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            tx = session.beginTransaction();
            String queryString = "SELECT DISTINCT tb.maTB FROM ThongTinSuDung ttd JOIN ttd.thietBi tb";
            Query<Integer> query = session.createQuery(queryString, Integer.class);
            List<Integer> maTBList = query.list();
            maTBArray = new String[maTBList.size()];
            for (int i = 0; i < maTBList.size(); i++) {
                maTBArray[i] = String.valueOf(maTBList.get(i));
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Lỗi khi lấy danh sách mã maTB: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return maTBArray;
    }

    public Date getTGTraByMaTB(int maTB) {
        session = HibernateUtils.getSessionFactory().openSession();
        Date tgTra = null;
        try {
            Query query = session.createQuery("SELECT t.TGTra FROM ThongTinSuDung t WHERE t.thietBi.MaTB = :maTB")
                    .setParameter("maTB", maTB);
            tgTra = (Date) query.uniqueResult();
        } catch (HibernateException e) {
            System.out.print("Lỗi khi lấy thời gian trả theo mã thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tgTra;
    }

    public Date getTGMuonByMaTB(int maTB) {
        session = HibernateUtils.getSessionFactory().openSession();
        Date tgMuon = null;
        try {
            Query query = session.createQuery("SELECT t.TGMuon FROM ThongTinSuDung t WHERE t.thietBi.MaTB = :maTB")
                    .setParameter("maTB", maTB);
            tgMuon = (Date) query.uniqueResult();
        } catch (HibernateException e) {
            System.out.print("Lỗi khi lấy thời gian trả theo mã thiết bị: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tgMuon;
    }

    public boolean checkMaTBExists(int maTB) {
        session = HibernateUtils.getSessionFactory().openSession();
        boolean exists = false;
        try {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM ThongTinSuDung t WHERE t.thietBi.MaTB = :maTB", Long.class)
                    .setParameter("maTB", maTB);
            Long count = query.uniqueResult();
            exists = count > 0;
        } catch (HibernateException e) {
            System.out.print("Lỗi khi kiểm tra mã thiết bị trong cơ sở dữ liệu: ");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return exists;
    }

    public ArrayList<ThongKeKhuHocTap> thongKeKhuHocTap(DateRange dateRange, String groupBy, String khoa, String nganh) {
        ArrayList<ThongKeKhuHocTap> list = new ArrayList<>();
        String queryTimeline = "";
        String fromDate = dateRange.getFromDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);
        String toDate = dateRange.getToDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);

        switch (groupBy) {
            case "date":
                queryTimeline = "DR.date";
                break;
            case "month":
                queryTimeline = "DATE_FORMAT(DR.date, '" + DateHelper.SQL_QUERY_MONTH_FORMAT + "-01')";
                break;
            case "year":
                queryTimeline = "DATE_FORMAT(DR.date, '" + DateHelper.SQL_QUERY_YEAR_FORMAT + "-01-01')";
                break;
            default:
        }

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("""
                            WITH RECURSIVE date_range AS (
                                SELECT DATE(:fromDate) AS date
                                UNION ALL
                                SELECT DATE_ADD(date, INTERVAL 1 DAY)
                                FROM date_range
                                WHERE DATE_ADD(date, INTERVAL 1 DAY) <= :toDate
                            ),
                            temp_table AS (
                                SELECT TTSD.*, Khoa, Nganh
                                FROM thongtinsd TTSD JOIN thanhvien TV ON TTSD.MaTV = TV.MaTV
                                WHERE DATE(TGVao) BETWEEN :fromDate AND :toDate AND Khoa LIKE :khoa AND Nganh LIKE :nganh
                            ),
                            result AS (
                                SELECT 
                            """);
        queryBuilder.append(queryTimeline);
        queryBuilder.append("""
                             AS timeline, COALESCE(COUNT(TGVao), 0) AS amount
                            FROM date_range DR LEFT JOIN temp_table T ON DR.date = DATE(T.TGVao)
                            GROUP BY
                            """);
        queryBuilder.append(queryTimeline);
        queryBuilder.append(" ORDER BY ");
        queryBuilder.append(queryTimeline);
        queryBuilder.append("""
                             DESC
                            )
                            SELECT * FROM result
                            """);
        session.beginTransaction();
        Query query = session.createSQLQuery(queryBuilder.toString())
                .addScalar("timeline", StandardBasicTypes.DATE)
                .addScalar("amount", StandardBasicTypes.INTEGER);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("khoa", "%" + khoa + "%");
        query.setParameter("nganh", "%" + nganh + "%");
        query.setResultTransformer(Transformers.aliasToBean(ThongKeKhuHocTap.class));
        list = (ArrayList<ThongKeKhuHocTap>) query.list();
        session.getTransaction().commit();
        return list;
    }

    public Date getMinDate() {
        session.beginTransaction();
        Query query = session.createNativeQuery("SELECT DATE(LEAST(MIN(TGVao), MIN(TGMuon), MIN(NgayXL))) AS min_date FROM thongtinsd, xuly");
        Date minDate = (Date) query.uniqueResult();
        session.getTransaction().commit();
        return minDate;
    }

    public ArrayList<ThongTinSuDung> getStatTTSD(DateRange dateRange, String device, boolean isTGTraNull) {
        ArrayList<ThongTinSuDung> list = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);
        String toDate = dateRange.getToDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("""
                            SELECT t 
                            FROM ThongTinSuDung t 
                            	JOIN t.thietBi tb
                            WHERE t.TGMuon BETWEEN DATE(:fromDate) AND DATE(:toDate)
                            	AND t.TGTra
                            """);
        queryBuilder.append(isTGTraNull ? " IS NULL " : " BETWEEN DATE(:fromDate) AND DATE(:toDate) ");
        queryBuilder.append("AND tb.TenTB LIKE :device ORDER BY t.TGMuon DESC");
        session.beginTransaction();
        Query query = session.createQuery(queryBuilder.toString(), ThongTinSuDung.class);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("device", "%" + device + "%");
        list = (ArrayList<ThongTinSuDung>) query.list();
        session.getTransaction().commit();
        return list;
    }

    public ArrayList<ThongKeKhuHocTap> getStatKhuHocTapUpToHour(DateRange dateRange, String khoa, String nganh) {
        ArrayList<ThongKeKhuHocTap> list = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(DateHelper.SQL_ROW_DATE_FORMATTER);

        LocalDateTime newToDate = null;
        LocalDate today = LocalDate.now();
        LocalDate datePart = dateRange.getToDate().toLocalDate();

        if (datePart.equals(today)) {
            LocalTime now = LocalTime.now();
            newToDate = LocalDateTime.of(datePart, LocalTime.of(now.getHour(), 0, 0));
        } else {
            newToDate = LocalDateTime.of(datePart, LocalTime.of(23, 0, 0));
        }
        String toDate = newToDate.format(DateHelper.SQL_ROW_DATE_FORMATTER);
        String toDateHour = newToDate.format(DateHelper.SQL_ROW_DATE_TIME_FORMATTER);
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("""
                            WITH RECURSIVE date_range AS (
                              SELECT DATE(:fromDate) + INTERVAL 0 HOUR AS date_hour
                              UNION ALL
                              SELECT DATE_ADD(date_hour, INTERVAL 1 HOUR)  
                              FROM date_range
                              WHERE DATE_ADD(date_hour, INTERVAL 1 HOUR) <= :toDateHour
                            ),
                            temp_table AS (
                              SELECT TTSD.*, Khoa, Nganh, CONCAT(DATE_FORMAT(TGVao, '%Y-%m-%d %H'), ':00') AS date_hour
                              FROM thongtinsd TTSD
                              JOIN thanhvien TV
                                ON TTSD.MaTV = TV.MaTV
                              WHERE DATE(TGVao) BETWEEN :fromDate AND :toDate AND Khoa LIKE :khoa AND Nganh LIKE :nganh
                            ),
                            result AS (
                              SELECT DR.date_hour AS timeline, COALESCE(COUNT(T.date_hour), 0) AS amount
                              FROM date_range DR
                              LEFT JOIN temp_table T
                                ON DR.date_hour = T.date_hour
                              GROUP BY DR.date_hour
                              ORDER BY DR.date_hour DESC
                            )
                            SELECT * FROM result
                            """);
        session.beginTransaction();
        Query query = session.createSQLQuery(queryBuilder.toString())
                .addScalar("timeline", StandardBasicTypes.TIMESTAMP)
                .addScalar("amount", StandardBasicTypes.INTEGER);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDateHour", toDateHour);
        query.setParameter("toDate", toDate);
        query.setParameter("khoa", "%" + khoa + "%");
        query.setParameter("nganh", "%" + nganh + "%");
        query.setResultTransformer(Transformers.aliasToBean(ThongKeKhuHocTap.class));
        list = (ArrayList<ThongKeKhuHocTap>) query.list();
        session.getTransaction().commit();
        return list;
    }
}
