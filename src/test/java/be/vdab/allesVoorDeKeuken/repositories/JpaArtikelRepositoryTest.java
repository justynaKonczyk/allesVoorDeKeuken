package be.vdab.allesVoorDeKeuken.repositories;

import be.vdab.allesVoorDeKeuken.domain.*;
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

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikelGroep.sql")
@Sql("/insertArtikel.sql")
public class JpaArtikelRepositoryTest
        extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String ARTIKELS = "artikels";

    @Autowired
    private EntityManager manager;

    @Autowired
    private JpaArtikelRepository repository;
    private FoodArtikel foodArtikel;
    private NonFoodArtikel nonFoodArtikel;
    private ArtikelGroep groep;

    @Before
    public void before() {
        groep = new ArtikelGroep("test");
        foodArtikel = new FoodArtikel("testfood", BigDecimal.ONE, BigDecimal.TEN, 7, groep);
        nonFoodArtikel =
                new NonFoodArtikel("testnonfood", BigDecimal.ONE, BigDecimal.TEN, 30, groep);
    }

    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testfood'", Long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testnonfood'", Long.class);
    }

    @Test
    public void findFoodArtikelById() {
        assertThat(((FoodArtikel)
                repository.findById(idVanTestFoodArtikel()).get())
                .getHoudbaarheid()).isEqualTo(7);
    }

    @Test
    public void findNonFoodArtikelById() {
        assertThat(((NonFoodArtikel)
                repository.findById(idVanTestNonFoodArtikel()).get())
                .getGarantie()).isEqualTo(30);
    }

    @Test
    public void findOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    public void createFoodArtikel() {
        manager.persist(groep);
        repository.add(foodArtikel);
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + foodArtikel.getId())).isOne();
    }

    @Test
    public void createNonFoodArtikel() {
        manager.persist(groep);
        repository.add(nonFoodArtikel);
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + nonFoodArtikel.getId())).isOne();
    }

    @Test
    public void findByNonExistingId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    public void findByNameContains() {
        assertThat(repository.findByNameContains("es"))
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like '%es%'", Integer.class))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("es"))
                .isSorted();
    }

    @Test
    public void readKortingen() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get().getKortingen())
                .containsOnly(new Korting(1, BigDecimal.TEN));
    }

    @Test
    public void artikelGroepLazyLoaded() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get()
                .getArtikelGroep().getNaam()).isEqualTo("test");

    }

    @Test
    public void findbyNameContains(){
        List<Artikel> artikels = repository.findByNameContains("es");
        manager.clear();
        assertThat(artikels)
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like %es%", Integer.class))
        .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(artikel -> assertThat(artikel).contains("es")).isSorted();
        assertThat(artikels).extracting(artikel -> artikel.getArtikelGroep().getNaam());
    }

//    @Test
//    public void increasePrice(){
//        assertThat(repository.increasePrice(BigDecimal.TEN))
//                .isEqualTo(super.countRowsInTable("artikels"));
//        assertThat(super.jdbcTemplate.queryForObject(
//                "select verkoopprijs from artikels where id=?", BigDecimal.class, idVanTestNonFoodArtikel()))
//                .isEqualByComparingTo("145");
//    }
}
