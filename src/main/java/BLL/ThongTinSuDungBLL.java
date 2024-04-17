/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import POJO.DateRange;
import POJO.ThongKeKhuHocTap;
import helper.DateHelper;
import hibernatemember.DAL.ThongTinSuDung;
import hibernatemember.DAL.ThongTinSuDungDAL;
import hibernatemember.DAL.XuLy;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ThongTinSuDungBLL {

    private ThongTinSuDungDAL thongtinsudungDAL;

    public ThongTinSuDungBLL() {
        thongtinsudungDAL = new ThongTinSuDungDAL();
    }

    public ArrayList<ThongTinSuDung> LoadDataSearch(int maTVTimKiem) throws ParseException {
        ArrayList<ThongTinSuDung> result = new ArrayList();
        for (ThongTinSuDung ttsd : LoadThongTinSuDung()) {
            String maTV = ttsd.getThanhVien().getMaTV() + "";
            if (maTV.contains(maTVTimKiem + "")) {
                result.add(ttsd);
            }
        }
        return result;
    }
    public int getMaxId(){
        return thongtinsudungDAL.getMaxId();
    }
    public boolean newThongTinSuDung(ThongTinSuDung c) {
        return thongtinsudungDAL.addThongTinSuDung(c);
    }

    public boolean updateThongTinSuDung(ThongTinSuDung c) {
        return thongtinsudungDAL.updateThongTinSuDung(c);
    }

    public ThongTinSuDung getThongTinSuDung(int MaTT) {
        ThongTinSuDung c = thongtinsudungDAL.getThongTinSuDung(MaTT);
        return c;
    }

    public ArrayList<ThongTinSuDung> LoadThongTinSuDung() throws ParseException {
        return thongtinsudungDAL.loadThongTinSuDung();
    }

    public int getMaTTAutoIncreasement() throws ParseException {
        ArrayList<ThongTinSuDung> listXuLy = LoadThongTinSuDung();
        int lastIndex = LoadThongTinSuDung().size() - 1;
        return listXuLy.get(lastIndex).getMaTT() + 1;
    }

    public String[] getListMaTB() {
        return thongtinsudungDAL.getListMaTB();
    }

//    public Date getTGTraByMaTB(int maTB) {
//        return thongtinsudungDAL.getTGTraByMaTB(maTB);
//    }
//
//    public Date getTGMuonByMaTB(int maTB) {
//        return thongtinsudungDAL.getTGMuonByMaTB(maTB);
//    }

    public boolean checkMaTBExists(int maTB) {
        return thongtinsudungDAL.checkMaTBExists(maTB);
    }

    public ArrayList<ThongTinSuDung> getDanhSachDatCho(int maTB) {
        return thongtinsudungDAL.getDanhSachDatCho(maTB);
    }

    public boolean thietBiDaDuocDatCho(int maTB, int maNguoiMuon) {
        ArrayList<ThongTinSuDung> danhSach = getDanhSachDatCho(maTB);
        for (ThongTinSuDung ttsd : danhSach) {
            Date date = ttsd.getTGDatcho();
            LocalDate ngayDatCho = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngayHienTai = LocalDate.now();
            // Kiểm tra xem ngày có bằng ngày hiện tại không và người đặt chổ có khác người mượn không
            if (ngayDatCho.isEqual(ngayHienTai) && ttsd.getThanhVien().getMaTV() != maNguoiMuon) {
                return true;
            }
        }
        return false;
    }
    
    public boolean thietBiDangDuocMuon(int maTB){
        return thongtinsudungDAL.thietBiDangDuocMuon(maTB);
    }

    public ArrayList<ThongKeKhuHocTap> thongKeKhuHocTap(DateRange dateRange, String groupBy, String khoa, String nganh) {
        return thongtinsudungDAL.thongKeKhuHocTap(dateRange, groupBy, khoa, nganh);
    }

    public Date getMinDate() {
        return thongtinsudungDAL.getMinDate();
    }

    public ArrayList<ThongTinSuDung> getStatTTSD(DateRange dateRange, String device, boolean isTGTraNull) {
        return thongtinsudungDAL.getStatTTSD(dateRange, device, isTGTraNull);
    }

    public ArrayList<ThongKeKhuHocTap> getStatKhuHocTapUpToHour(DateRange dateRange, String khoa, String nganh) {
        ArrayList<ThongKeKhuHocTap> result = thongtinsudungDAL.getStatKhuHocTapUpToHour(dateRange, khoa, nganh);
        for (ThongKeKhuHocTap i : result) {
            i.setTimeline(DateHelper.subtractHours(i.getTimeline(), 7));
        }
        return result;
    }

}
