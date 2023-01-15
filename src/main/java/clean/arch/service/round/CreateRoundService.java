package clean.arch.service.round;

import clean.arch.domain.entity.Round;
import clean.arch.repository.RoundRepository;
import clean.arch.usecase.CreatingRoundUsecase;

import java.time.LocalDate;

import static clean.arch.exception.RoundException.*;
import static java.time.DayOfWeek.*;
import static java.time.LocalDate.*;

public class CreateRoundService implements CreatingRoundUsecase {

    private final RoundRepository roundRepository;

    public CreateRoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Override
    public void create(Request request) {
        validate(
                request.name(),
                request.drawnAt()
        );
        var prevBalance = restOfRewardMoney(request.drawnAt());
        roundRepository.save(
                new Round(
                        request.name(),
                        request.drawnAt(),
                        prevBalance
                )
        );
    }

    private void validate(String name, LocalDate drawnAt) {
        nameValidator(name);
        drawnAtValidator(drawnAt);
    }

    void nameValidator(String name) {
        roundRepository.findByName(name)
                .ifPresent(round -> {
                    throw new DuplicatedNameException();
                });
    }

    void drawnAtValidator(LocalDate drawnAt) {
        if (drawnAt.isBefore(now())) {
            throw new DrawnAtPastDateException();
        }
        if (drawnAt.getDayOfWeek() != SATURDAY) {
            throw new DrawnAtNotSaturdayException();
        }
        roundRepository.findByDrawnAt(drawnAt)
                .ifPresent(round -> {
                    throw new DuplicatedDrawnAtException();
                });
    }

    int restOfRewardMoney(LocalDate currentRoundDrawnAt) {
        var lastRoundDrawnAt = currentRoundDrawnAt.minusDays(7);
        return roundRepository.findByDrawnAt(lastRoundDrawnAt)
                .map(Round::restOfPrevRoundRewardMoney)
                .orElse(0);
    }
}
