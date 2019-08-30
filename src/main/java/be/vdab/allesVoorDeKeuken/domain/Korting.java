package be.vdab.allesVoorDeKeuken.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Korting implements Serializable {
    private static final long serialVersionUID = 1L;

    private int vanafAantal;
    private BigDecimal percentage;

    public Korting(int vanafAantal, BigDecimal percentage) {
        this.vanafAantal = vanafAantal;
        this.percentage = percentage;
    }

    protected Korting() {
    }

    public int getVanafAantal() {
        return vanafAantal;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    @Override
    public boolean equals(Object o) {
       if (! (o instanceof  Korting)) {
           return false;
       }
       Korting andereKorting = (Korting) o;
       return vanafAantal == andereKorting.vanafAantal;
    }

    @Override
    public int hashCode() {
        return vanafAantal;
    }
}
