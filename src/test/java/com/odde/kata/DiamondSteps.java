package com.odde.kata;

import com.github.leeonky.dal.DAL;
import io.cucumber.java.en.Then;

import static com.github.leeonky.dal.Assertions.expect;

public class DiamondSteps {
    public static String diamond(DiamondGame diamondGame, String character) {
        return diamondGame.diamond(character.charAt(0));
    }

    @Then("the diamond should:")
    public void theDiamondShould(String expression) {
        DAL.getInstance().getRuntimeContextBuilder().registerStaticMethodExtension(DiamondSteps.class);
        expect(new DiamondGame()).should(expression);
    }
}
