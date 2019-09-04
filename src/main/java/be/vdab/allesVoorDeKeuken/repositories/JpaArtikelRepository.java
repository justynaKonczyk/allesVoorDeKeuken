package be.vdab.allesVoorDeKeuken.repositories;

import be.vdab.allesVoorDeKeuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
class JpaArtikelRepository implements ArtikelRepository {

    private final EntityManager manager;

    JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(long id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }

    @Override
    public void add(Artikel artikel) {
        manager.persist(artikel);
    }

    @Override
    public List<Artikel> findByNaamContains(String woord) {
//        return manager.createNamedQuery("Artikel.findByNameContains", Artikel.class)
//                .setParameter("if", '%' + woord + '%').getResultList();

            return manager.createNamedQuery("Artikel.findByNaamContains", Artikel.class)
                    .setParameter("zoals", '%' + woord + '%')
                    .setHint("javax.persistence.loadgraph",
                            manager.createEntityGraph(Artikel.MET_ARTIKELGROEP))
                    .getResultList();
        }


    @Override
    public int increasePrice(BigDecimal percentage) {
        BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        return manager.createNamedQuery("Artikel.increasePrice" )
                .setParameter("factor", factor)
                .executeUpdate();
    }

}

