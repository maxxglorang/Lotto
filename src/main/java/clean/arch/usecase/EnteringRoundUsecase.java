package clean.arch.usecase;

import clean.arch.domain.entity.Round;

public interface EnteringRoundUsecase {
    Round enter(Request request);

    record Request(
            String roundName,
            int paidMoney
    ) {}
}
