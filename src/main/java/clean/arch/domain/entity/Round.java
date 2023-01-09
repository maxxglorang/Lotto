package clean.arch.domain.entity;

import java.time.LocalDate;
import java.util.List;

public class Round {
    private String name;
    private LocalDate drawnAt;
    private int startRewardMoney;
    private int restRewardMoney;

    private List<Integer> resultNumbers;

    public Round(
            String name,
            LocalDate drawnAt,
            int startRewardMoney
    ) {
        this.name = name;
        this.drawnAt = drawnAt;
        this.startRewardMoney = startRewardMoney;
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

    public int startRewardMoney() {
        return startRewardMoney;
    }

    public void setStartRewardMoney(int startRewardMoney) {
        this.startRewardMoney = startRewardMoney;
    }

    public int restRewardMoney() {
        return restRewardMoney;
    }

    public void setRestRewardMoney(int restRewardMoney) {
        this.restRewardMoney = restRewardMoney;
    }
}