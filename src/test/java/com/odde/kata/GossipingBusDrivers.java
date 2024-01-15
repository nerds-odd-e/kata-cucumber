package com.odde.kata;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.Arrays;
import java.util.List;

import static com.github.leeonky.dal.Assertions.expect;

public class GossipingBusDrivers {

    private Simulator simulator;

    @Given("the following drivers with their route stops:")
    public void theFollowingDriversWithTheirRouteStops(DataTable table) {
        simulator = new Simulator(table.asLists().stream().map(stops -> Arrays.asList(stops.get(0).split(" "))).toList());
    }

    @Then("it should:")
    public void itShould(String expression) {
        expect(simulator).should(expression);
    }

    public static class Simulator {
        private final List<List<String>> driversAndStops;

        public Simulator(List<List<String>> driversAndStops) {
            this.driversAndStops = driversAndStops;
        }

        public int simulate() {
            if (driversAndStops.size() > 1) {
                for (int stopIndex = 0; stopIndex < driversAndStops.get(0).size(); stopIndex++)
                    if (driversAndStops.get(0).get(stopIndex).equals(driversAndStops.get(1).get(stopIndex))) {
                        return stopIndex + 1;
                    }
                throw new IllegalStateException();
            }
            return 1;
        }
    }
}
