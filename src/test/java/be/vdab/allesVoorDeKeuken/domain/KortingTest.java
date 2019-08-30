package be.vdab.allesVoorDeKeuken.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class KortingTest {

    private Korting korting1, nogEenskorting1, korting2;

    @Before
    public void before(){
        korting1 = new Korting(1, BigDecimal.ONE);
        nogEenskorting1 = new Korting(1, BigDecimal.ONE);
        korting2 = new Korting(2, BigDecimal.TEN);
    }

    @Test
    public void kortingsAreTheSame(){
        assertThat(korting1).isEqualTo(nogEenskorting1);
    }

    @Test
    public void KortingsAreDifferent(){
        assertThat(korting1).isNotEqualTo(korting2);
    }

    @Test
    public void kortingsIsNotNull(){
        assertThat(korting1).isNotEqualTo(null);
    }

    @Test
    public void kortingsIsDifferentThanOtherTypeObject(){
        assertThat(korting1).isNotEqualTo("");
    }

    @Test
    public void sameKortingsGivesSameHashCode(){
        assertThat(korting1).hasSameHashCodeAs(nogEenskorting1);
    }
}

