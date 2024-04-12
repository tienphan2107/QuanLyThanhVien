package hibernatemember.DAL;

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
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGVao;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGMuon;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TGTra;    
    // getThanhVien().getMaTV

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private ThanhVien thanhVien;

    // getThietBi().getMaTB
    @ManyToOne
    @JoinColumn(name = "MaTB")
    private ThietBi thietBi;
}
