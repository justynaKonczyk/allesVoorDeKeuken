package be.vdab.allesVoorDeKeuken.repositories;

import be.vdab.allesVoorDeKeuken.domain.Artikel;

import java.util.Optional;

public interface ArtikelRepository {

    Optional<Artikel> findById(long id);
    void add(Artikel artikel);
}