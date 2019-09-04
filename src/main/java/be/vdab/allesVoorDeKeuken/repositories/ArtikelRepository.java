package be.vdab.allesVoorDeKeuken.repositories;

import be.vdab.allesVoorDeKeuken.domain.Artikel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArtikelRepository {

    Optional<Artikel> findById(long id);
    void add(Artikel artikel);
    List<Artikel> findByNaamContains(String woord);
    int increasePrice(BigDecimal percentage);

}