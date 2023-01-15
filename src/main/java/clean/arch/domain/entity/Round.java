package clean.arch.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Round {
    private String name;
    private LocalDate drawnAt;
    private int restOfPrevRoundRewardMoney;
    private int balance;
    private List<Integer> drawnNumbers;
    private List<Game> games;

    public Round(
            String name,
            LocalDate drawnAt,
            int startRewardMoney
    ) {
        this.name = name;
        this.drawnAt = drawnAt;
        this.restOfPrevRoundRewardMoney = startRewardMoney;
        this.balance = restOfPrevRoundRewardMoney;
        this.drawnNumbers = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public void updateBalance(int money) {
        this.balance += money;
    }

    public int balance() {
        return balance + restOfPrevRoundRewardMoney;
    }

    public List<Integer> drawnNumbers() {
        return drawnNumbers;
    }

    public void setDrawnNumbers(List<Integer> drawnNumbers) {
        this.drawnNumbers = drawnNumbers;
    }

    public List<Game> games() {
        return games;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate drawnAt() {
        return drawnAt;
    }

    public void setDrawnAt(LocalDate drawnAt) {
        this.drawnAt = drawnAt;
    }

    public int restOfPrevRoundRewardMoney() {
        return restOfPrevRoundRewardMoney;
    }

    public void setRestOfPrevRoundRewardMoney(int restOfPrevRoundRewardMoney) {
        this.restOfPrevRoundRewardMoney = restOfPrevRoundRewardMoney;
    }
}