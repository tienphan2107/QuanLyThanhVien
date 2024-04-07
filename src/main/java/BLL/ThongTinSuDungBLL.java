/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import POJO.DateRange;
import POJO.ThongKeKhuHocTap;
import hibernatemember.DAL.ThongTinSuDung;
import hibernatemember.DAL.ThongTinSuDungDAL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ThongTinSuDungBLL {
    private ThongTinSuDungDAL thongtinsudungDAL;
    
    public ThongTinSuDungBLL()
    {
        thongtinsudungDAL = new ThongTinSuDungDAL();
    }
    
    public List loadThongTinSuDung()
    {
        List list;
        list = thongtinsudungDAL.loadThongTinSuDung();
        
        return list;
    }
    
    public void newThongTinSuDung(ThongTinSuDung c)
    {
        thongtinsudungDAL.addThongTinSuDung(c);
    }
    
    public ThongTinSuDung getThongTinSuDung(int MaTT)
    {
        ThongTinSuDung c = thongtinsudungDAL.getThongTinSuDung(MaTT);
        return c;
    }
    
    public ArrayList<ThongKeKhuHocTap> thongKeKhuHocTap(DateRange dateRange, String groupBy, String khoa, String nganh) {
        return thongtinsudungDAL.thongKeKhuHocTap(dateRange, groupBy, khoa, nganh);
    }
    
    public Date getMinDate() {
        return thongtinsudungDAL.getMinDate();
    }
}
