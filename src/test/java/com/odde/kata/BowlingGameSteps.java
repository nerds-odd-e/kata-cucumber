package com.odde.kata;

import com.github.leeonky.dal.DAL;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.github.leeonky.dal.Assertions.expect;
import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameSteps {

    private BowlingGame bowlingGame = new BowlingGame();

    @Then("score is {int}")
    public void scoreIs(int score) {
        assertThat(bowlingGame.score()).isEqualTo(score);
    }

    @When("roll")
    public void roll(DataTable dataTable) {
        dataTable.asList().forEach(pin -> bowlingGame.roll(Integer.parseInt(pin)));
    }

    @Then("game should")
    public void gameShould(String expression) {
        DAL.getInstance().getRuntimeContextBuilder().registerStaticMethodExtension(BowlingGameSteps.class);
        expect(bowlingGame).should(expression);
    }

    public static BowlingGame gameRoll(BowlingGame game, int pin) {
        game.roll(pin);
        return game;
    }
}
