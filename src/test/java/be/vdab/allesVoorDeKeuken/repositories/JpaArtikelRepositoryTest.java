package be.vdab.allesVoorDeKeuken.repositories;

import be.vdab.allesVoorDeKeuken.domain.Artikel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikel.sql")
public class JpaArtikelRepositoryTest
        extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String ARTIKELS = "artikels";

    private Artikel artikel;

    @Before
    public void before(){
        artikel = new Artikel("test", BigDecimal.TEN, BigDecimal.TEN);
    }


    @Autowired
    private JpaArtikelRepository repository;


    private long idOfArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='test'", Long.class);
    }

    @Test
    public void findById() {
        assertThat(repository.findById(idOfArtikel()).get().getNaam())
                .isEqualTo("test");
    }

    @Test
    public void findByNonExistingId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    public void add(){
        repository.add(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    public void findByNameContains(){
        assertThat(repository.findByNameContains("es"))
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like '%es%'", Integer.class))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("es"))
                .isSorted();
    }
}
