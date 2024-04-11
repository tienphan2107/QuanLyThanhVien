package POJO;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ThongKeKhuHocTap {

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeline;
    private int amount;

    public ThongKeKhuHocTap() {
    }

    public ThongKeKhuHocTap(Date timeline, int amount) {
        this.timeline = timeline;
        this.amount = amount;
    }

    public Date getTimeline() {
        return timeline;
    }

    public void setTimeline(Date timeline) {
        this.timeline = timeline;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
