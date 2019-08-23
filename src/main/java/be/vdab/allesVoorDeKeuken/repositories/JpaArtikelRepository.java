package be.vdab.allesVoorDeKeuken.repositories;

import be.vdab.allesVoorDeKeuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public List<Artikel> findByNameContains(String woord) {
        return manager.createNamedQuery("Artikel.findByNameContains", Artikel.class)
                .setParameter("if", '%' + woord + '%').getResultList();
    }
}

