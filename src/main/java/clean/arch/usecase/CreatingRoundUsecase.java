package clean.arch.usecase;

import java.time.LocalDate;

public interface CreatingRoundUsecase {
    void create(Request request);

    record Request(
            String name,
            LocalDate drawnAt
    ) {}
}
