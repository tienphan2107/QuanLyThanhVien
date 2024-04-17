package hibernatemember.DAL;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "thongtinsd")
public class ThongTinSuDung {

    @Id
    private int MaTT;

//    @Column
//    private int MaTV;
//    @Column
//    private int MaTB;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGVao;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGMuon;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGTra;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGDatcho;
    // getThanhVien().getMaTV

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private ThanhVien thanhVien;

    // getThietBi().getMaTB
    @ManyToOne
    @JoinColumn(name = "MaTB", nullable = true)
    private ThietBi thietBi;
    

    public ThongTinSuDung() {
    }

    public ThongTinSuDung(int MaTT, int MaTV, Integer MaTB, Date TGVao, Date TGMuon, Date TGTra) {
        this.MaTT = MaTT;
        this.thanhVien = new ThanhVien();
        this.thanhVien.setMaTV(MaTV);
        if (MaTB != null) {
            this.thietBi = new ThietBi();
            this.thietBi.setMaTB(MaTB);
        }
        this.TGVao = TGVao;
        this.TGMuon = TGMuon;
        this.TGTra = TGTra;
    }


}
