package com.odde.kata;

import io.cucumber.java.en.Given;

import static com.github.leeonky.dal.Assertions.expect;

public class FizzBuzz {
    public class Game {
    }

    @Given("Fizz buzz:")
    public void fizz_buzz(String expression) {
        expect(new Game()).should(expression);
    }
}
