package com.odde.kata;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.github.leeonky.dal.Assertions.expect;

public class GossipingBusDrivers {

    private Simulator simulator;

    @Given("the following drivers with their route stops:")
    public void theFollowingDriversWithTheirRouteStops(DataTable table) {
        simulator = new Simulator(table.asLists().stream().map(driver -> createStops(driver.get(0))).toList());
    }

    @Then("it should:")
    public void itShould(String expression) {
        expect(simulator).should(expression);
    }

    private List<String> createStops(String stops) {
        if (stops.contains("..")) {
            var result = stops.split(" ");
            var start = Integer.parseInt(result[0]);
            var end = Integer.parseInt(result[2]);
            return IntStream.range(start, end + 1).mapToObj(String::valueOf).toList();
        } else {
            return Arrays.asList(stops.split(" "));
        }
    }

    public static class Simulator {
        private final List<Driver> driversAndStops;

        public Simulator(List<List<String>> driversAndStops) {
            this.driversAndStops = driversAndStops.stream().map(Driver::new).toList();
        }

        public int simulate() {
            if (driversAndStops.size() == 1) {
                return 1;
            }
            for (int step = 0; step < 480; step++) {
                if (driversAndStops.size() == 2) {
                    driversAndStops.get(0).gossip(step, driversAndStops.get(1));
                    if (driversAndStops.stream().allMatch(driver -> driver.allGossipped(driversAndStops.size()))) {
                        return step + 1;
                    }
                }
                if (driversAndStops.size() == 3) {
                    driversAndStops.get(0).gossip(step, driversAndStops.get(1));
                    driversAndStops.get(0).gossip(step, driversAndStops.get(2));
                    driversAndStops.get(1).gossip(step, driversAndStops.get(2));
                    if (driversAndStops.stream().allMatch(driver -> driver.allGossipped(driversAndStops.size()))) {
                        return step + 1;
                    }
                }
            }
            throw new IllegalStateException();
        }

    }
}
