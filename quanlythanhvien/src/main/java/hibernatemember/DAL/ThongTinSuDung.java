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
@Table(name = "thongtinsd")
public class ThongTinSuDung {

    @Id
    private int MaTT;
    @Column
    private int MaTV;
    @Column
    private int MaTB;
    @Column
    private Date TGVao;
    @Column
    private Date TGMuon;
    @Column
    private Date TGTra;
}
