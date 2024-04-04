/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernatemember.DAL;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author DELL
 */

@Data
@Entity
@Table(name = "xuly")
public class XuLy {
    @Id
    private int MaXL;
    @Column
    private int MaTV;
    @Column
    private String HinhThucXL;
    @Column(nullable = true)
    private Integer SoTien;
    @Column
    private Date NgayXL;
    @Column
    private int TrangThaiXL;

    public XuLy() {
    }
    
    

    public XuLy(int MaXL, int MaTV, String HinhThucXL, int SoTien, Date NgayXL, int TrangThaiXL) {
        this.MaXL = MaXL;
        this.MaTV = MaTV;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }
}
