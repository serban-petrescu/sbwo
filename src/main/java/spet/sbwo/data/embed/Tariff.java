package spet.sbwo.data.embed;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Tariff {
    @Column(name = "C_PRICE")
    private BigDecimal price;

    @Column(name = "C_ADVANCE")
    private BigDecimal advance;

    public Tariff() {
        super();
    }

    public Tariff(BigDecimal price, BigDecimal advance) {
        this.price = price;
        this.advance = advance;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAdvance() {
        return advance;
    }

    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
    }

}
