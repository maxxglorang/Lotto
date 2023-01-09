package clean.arch.service;

import clean.arch.domain.entity.Round;
import clean.arch.repository.RoundRepository;
import clean.arch.usecase.CreatingRoundUsecase;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
                .map(Round::startRewardMoney)
                .orElse(0);
    }

    static class DuplicatedDrawnAtException extends RuntimeException {}
    static class DuplicatedNameException extends RuntimeException {}
    static class DrawnAtPastDateException extends RuntimeException {}
    static class DrawnAtNotSaturdayException extends RuntimeException {}
}
