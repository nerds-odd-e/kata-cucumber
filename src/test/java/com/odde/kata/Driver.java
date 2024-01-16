package com.odde.kata;

import java.util.List;

public record Driver(List<String> driverStops) {
    String getStopByStep(int step) {
        return driverStops.get(step % driverStops.size());
    }
}