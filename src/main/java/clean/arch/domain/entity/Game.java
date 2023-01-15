package clean.arch.domain.entity;

import clean.arch.util.RandomNumber;

import java.util.List;
import java.util.UUID;

import static clean.arch.common.constant.Property.*;

public class Game {
    private String gameId;
    private String roundName;
    private List<Integer> numbers;

    public Game(String roundName) {
        this.gameId = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
        this.roundName = roundName;
        this.numbers = new RandomNumber(LOTTO_MIN, LOTTO_MAX).generator(LOTTO_NUMBER_OF_COUNT);
    }

    public String gameId() {
        return gameId;
    }
    public String roundName() {
        return roundName;
    }

    public List<Integer> numbers() {
        return numbers;
    }
}
