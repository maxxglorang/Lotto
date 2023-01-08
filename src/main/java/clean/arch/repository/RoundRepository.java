package clean.arch.repository;

import clean.arch.domain.entity.Round;

import java.time.LocalDate;
import java.util.Optional;

public interface RoundRepository {
    void save(Round round);

    Optional<Round> findByName(String name);

    Optional<Round> findByDrawnAt(LocalDate drawnAt);
}
