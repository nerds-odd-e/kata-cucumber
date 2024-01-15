package com.odde.kata;

import io.cucumber.java.en.Then;

import static com.github.leeonky.dal.Assertions.expect;

public class DiamondSteps {
    @Then("the diamond should:")
    public void theDiamondShould(String expression) {
        expect(new DiamondGame()).should(expression);
    }
}
