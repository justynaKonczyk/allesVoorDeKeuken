package be.vdab.allesVoorDeKeuken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel {
    private static final long serialVersionUID = 1L;

    private int garantie;

    public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs,
                          int garantie, ArtikelGroep artikelGroep) {
        super(naam, aankoopprijs, verkoopprijs, artikelGroep);
        this.garantie = garantie;
    }

    protected NonFoodArtikel() {
    }

    public int getGarantie() {
        return garantie;
    }

}
