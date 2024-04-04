/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import hibernatemember.DAL.ThietBi;
import hibernatemember.DAL.ThietBiDAL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ThietBiBLL implements ActionListener {
    private GUI.Panel.ThietBiPanel tb;
    private ThietBiDAL thietbiDAL;
    
    
    public ThietBiBLL()
    {
        thietbiDAL = new ThietBiDAL();
    }
    
    public ThietBiBLL(GUI.Panel.ThietBiPanel tb)
    {
        this.tb = tb;
        thietbiDAL = new ThietBiDAL();
    }
    
    public List loadThietBi()
    {
        List list;
        list = thietbiDAL.loadThietBi();
        
        return list;
    }
    
    public void newThietBi(ThietBi c)
    {
        thietbiDAL.addThietBi(c);
    }
    
    public ThietBi getThietBi(int thietbiID)
    {
        ThietBi c = thietbiDAL.getThietBi(thietbiID);
        return c;
    }
    
    public void actionPerformed(ActionEvent e) {
        
    }
}
