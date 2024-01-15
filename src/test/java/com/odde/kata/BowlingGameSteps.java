package com.odde.kata;

import com.github.leeonky.dal.DAL;
import com.github.leeonky.dal.runtime.JavaClassPropertyAccessor;
import com.github.leeonky.dal.runtime.RuntimeContextBuilder;
import com.github.leeonky.util.BeanClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.github.leeonky.dal.Assertions.expect;
import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameSteps {

    private BowlingGame bowlingGame;

    public static BowlingGame makeRoll(BowlingGame game, int pin) {
        game.roll(pin);
        return game;
    }

    public static TestGame bowling(BowlingGame game) {
        return new TestGame(game);
    }

    @Before
    public void init() {
        bowlingGame = new BowlingGame();
    }

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
        RuntimeContextBuilder contextBuilder = DAL.getInstance().getRuntimeContextBuilder();
        contextBuilder.registerStaticMethodExtension(BowlingGameSteps.class)
                .registerPropertyAccessor(TestGame.class, new JavaClassPropertyAccessor<>(BeanClass.create(TestGame.class)) {
                    @Override
                    public Object getValue(TestGame instance, Object property) {
                        if (property.equals("x")) {
                            instance.game.roll(10);
                            return instance;
                        }
                        try {
                            int pin = Integer.parseInt(property.toString());
                            instance.game.roll(pin);
                            return instance;
                        } catch (NumberFormatException ignore) {
                            return super.getValue(instance, property);
                        }
                    }
                });

        contextBuilder.getConverter().addTypeConverter(TestGame.class, Integer.class, TestGame::score);

        expect(bowlingGame).should(expression);
    }

    public static class TestGame {
        public final BowlingGame game;

        public TestGame(BowlingGame game) {
            this.game = game;
        }

        public int score() {
            return game.score();
        }
    }
}
