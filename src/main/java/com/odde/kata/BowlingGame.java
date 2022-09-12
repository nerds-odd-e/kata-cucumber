package com.odde.kata;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private final List<Integer> pins = new ArrayList<>();
    private int score;

    public int score() {
        return score;
    }

    public void roll(int pin) {
        if (pins.size() == 20 && !isSecondStrikeBonus() && !(isFirstStrikeBonus() && isNotLastBonusForLastStrike()) && !isSpareBonus() ||
                pins.size() == 21 && !isSecondStrikeBonus() && !(isFirstStrikeBonus() && isNotLastBonusForLastStrike()) ||
                pins.size() == 22 && !isSecondStrikeBonus() ||
                pins.size() == 24) {
            throw new RuntimeException("Game is over");
        }
        if (isSecondStrikeBonus()) {
            score += pin;
        }
        if (isFirstStrikeBonus() && isNotLastBonusForLastStrike()) {
            score += pin;
        }
        if (isSpareBonus()) {
            score += pin;
        }
        if (pins.size() < 20) {
            score += pin;
        }
        addPin(pin);
    }

    private void addPin(int pin) {
        pins.add(pin);
        if (pins.size() % 2 == 1 && pin == 10) {
            pins.add(0);
        }
    }

    private boolean isFirstStrikeBonus() {
        return pins.size() >= 2 && pins.size() % 2 == 0 && pins.get(pins.size() - 2) == 10;
    }

    private boolean isNotLastBonusForLastStrike() {
        return pins.size() < 22;
    }

    private boolean isSecondStrikeBonus() {
        if (isFirstStrikeBonus()) {
            return pins.size() >= 4 && pins.size() % 2 == 0 && pins.get(pins.size() - 4) == 10;
        }
        return pins.size() >= 3 && pins.size() % 2 == 1 && pins.get(pins.size() - 3) == 10;
    }

    private boolean isSpareBonus() {
        return pins.size() >= 2 && pins.size() % 2 == 0 && secondLastPin() + lastPin() == 10 && secondLastPin() != 10;
    }

    private Integer lastPin() {
        return pins.get(pins.size() - 1);
    }

    private Integer secondLastPin() {
        return pins.get(pins.size() - 2);
    }
}
