package clean.arch.repository;

import clean.arch.domain.entity.Game;

public interface GameRepository {
    Game save(Game game);
}
