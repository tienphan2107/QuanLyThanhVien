/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author DELL
 */
@Data
@Entity
@Table(name = "thanhvien")
public class ThanhVien {
    @Id
    private int MaTV;
    @Column
    private String HoTen;
    @Column
    private String Khoa;
    @Column
    private String Nganh;
    @Column
    private String SDT; 
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thanhVien", cascade = CascadeType.ALL)
    private List<ThongTinSuDung> thongTinSuDung;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thanhVien", cascade = CascadeType.ALL)
    private List<XuLy> xuLy;
    
    public ThanhVien() {}

    public ThanhVien(int MaTV, String HoTen, String Khoa, String Nganh, String SDT) {
        this.MaTV = MaTV;
        this.HoTen = HoTen;
        this.Khoa = Khoa;
        this.Nganh = Nganh;
        this.SDT = SDT;
    }
}
