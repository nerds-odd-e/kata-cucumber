package com.odde.kata;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
}
