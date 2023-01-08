package clean.arch.usecase;

public interface JoiningRoundUsecase {
    void purchase(Request request);

    record Request(
            String roundName,
            int amount
    ) {}
}
