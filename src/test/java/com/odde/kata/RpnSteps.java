package com.odde.kata;

import io.cucumber.java.en.Then;

import static com.github.leeonky.dal.Assertions.expect;

public class RpnSteps {


    @Then("rpn should be")
    public void rpnShouldBe(String expression) {
        expect(new Rpn()).should(expression);
    }
}
