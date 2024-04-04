/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import hibernatemember.DAL.XuLy;
import hibernatemember.DAL.XuLyDAL;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class XuLyBLL {
    XuLyDAL xuLyDAL;
    
    public XuLyBLL(){
        xuLyDAL = new XuLyDAL();
    }
    
    public ArrayList<XuLy> LoadXuLy(){
        return xuLyDAL.loadXuLy();
    }
    
    public XuLy GetXuLy(int xuLyID){
        return xuLyDAL.getXuLy(xuLyID);
    }
    
    public boolean AddXuLy(XuLy xuLy){
        return xuLyDAL.addXuLy(xuLy);
    }
    
    public boolean UpdateXuLy(XuLy xuLy){
        return xuLyDAL.updateXuLy(xuLy);
    }
    
    public boolean DeleteXuLy(XuLy xuLy){
        return xuLyDAL.deleteXuLy(xuLy);
    }
}
