package clean.arch.usecase;

public interface EnteringRoundUsecase {
    void enter(Request request);

    record Request(
            String roundName,
            int amount
    ) {}
}
