package clean.arch.service.round;

import clean.arch.domain.entity.Game;
import clean.arch.domain.entity.Round;
import clean.arch.repository.GameRepository;
import clean.arch.repository.RoundRepository;
import clean.arch.usecase.EnteringRoundUsecase;

import static clean.arch.common.constant.Property.*;
import static clean.arch.exception.GameException.*;
import static clean.arch.exception.RoundException.*;

public class EnterRoundService implements EnteringRoundUsecase {
    private RoundRepository roundRepository;
    private GameRepository gameRepository;

    public EnterRoundService(
            RoundRepository roundRepository,
            GameRepository gameRepository
    ) {
        this.roundRepository = roundRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Round enter(Request request) {
        var round = roundRepository.findByName(request.roundName())
                .orElseThrow(NotFoundRoundException::new);
        var count = gameCount(request.paidMoney());
        for (var i = 0; i < count; i++) {
            var game = gameRepository.save(new Game(round.name()));
            round.games().add(game);
            round.updateBalance(COST_PER_GAME);
        }
        roundRepository.update(round);
        return round;
    }

    public Integer gameCount(int inputMoney) {
        if ((inputMoney / COST_PER_GAME) < 1) {
            throw new LowMoneyException();
        }
        return inputMoney / COST_PER_GAME;
    }
}
