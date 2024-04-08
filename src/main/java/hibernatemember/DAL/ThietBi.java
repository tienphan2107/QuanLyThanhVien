/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.util.List;
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
@Table(name = "thietbi")
public class ThietBi {
    @Id
    private int MaTB;
    @Column
    private String TenTB;
    @Column
    private String MoTaTB;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thietBi")
    private List<ThongTinSuDung> thongTinSuDung;
    
    public ThietBi() {}

    public ThietBi(int MaTB, String TenTB, String MoTaTB) {
        this.MaTB = MaTB;
        this.TenTB = TenTB;
        this.MoTaTB = MoTaTB;
    }
}
