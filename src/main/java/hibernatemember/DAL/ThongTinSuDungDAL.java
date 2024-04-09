/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import POJO.DateRange;
import POJO.ThongKeKhuHocTap;
import helper.DateHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

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

    public ThongTinSuDung getThongTinSuDung(int MaTT) {
        ThongTinSuDung c = session.get(ThongTinSuDung.class, MaTT);
        return c;
    }

    public void addThongTinSuDung(ThongTinSuDung c) {
        session.save(c);

    }

    public void updateThongTinSuDung(ThongTinSuDung c) {
        session.update(c);

    }

    public void deleteThongTinSuDung(ThongTinSuDung c) {
        session.delete(c);
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
}
