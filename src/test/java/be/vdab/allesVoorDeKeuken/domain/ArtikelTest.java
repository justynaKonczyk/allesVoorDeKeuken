package be.vdab.allesVoorDeKeuken.domain;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ArtikelTest {

    private Artikel artikel1;
    private ArtikelGroep groep1;
    private ArtikelGroep groep2;

    @Before
    public void before() {
        groep1 = new ArtikelGroep("test");
        groep2 = new ArtikelGroep("test2");
        artikel1 = new FoodArtikel("test",BigDecimal.ONE,BigDecimal.ONE,1,groep1);
    }
    @Test
    public void groep1EnArtikel1ZijnVerbonden() {
        assertThat(groep1.getArtikels()).containsOnly(artikel1);
        assertThat(artikel1.getArtikelGroep()).isEqualTo(groep1);
    }
    @Test
    public void artikel1VerhuistNaarGroep2() {
        artikel1.setArtikelGroep(groep2);
        assertThat(groep1.getArtikels()).isEmpty();
        assertThat(groep2.getArtikels()).containsOnly(artikel1);
    }
    @Test
    public void nullAlsArtikelGroepInDeConstructorMislukt() {
        assertThatNullPointerException().isThrownBy(
                () -> new FoodArtikel("test", BigDecimal.ONE, BigDecimal.ONE, 1, null));
    }
    @Test
    public void nullAlsArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(
                ()->artikel1.setArtikelGroep(null));
    }


}
