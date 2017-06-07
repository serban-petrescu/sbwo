package spet.sbwo.control.channel.expertise;

import spet.sbwo.control.channel.base.BaseChannel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpertiseFineChannel extends BaseChannel {
    private LocalDate date;
    private BigDecimal sum;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

}
