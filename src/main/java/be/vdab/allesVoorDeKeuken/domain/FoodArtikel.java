package be.vdab.allesVoorDeKeuken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.swing.*;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {
    private static final long serialVersionUID = 1L;

    private int houdbaarheid;

    public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs,
                       int houdbaarheid, ArtikelGroep artikelGroep) {
        super(naam, aankoopprijs, verkoopprijs, artikelGroep);
        this.houdbaarheid = houdbaarheid;
    }

    protected FoodArtikel() {
    }


    public int getHoudbaarheid() {
        return houdbaarheid;
    }

}
