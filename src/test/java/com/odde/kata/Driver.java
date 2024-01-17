package com.odde.kata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Driver {
    private final List<String> driverStops;
    private final Set<Driver> alreadyGossipDrivers = new HashSet<>() {{
        add(Driver.this);
    }};

    public Driver(List<String> driverStops) {
        this.driverStops = driverStops;
    }

    public void gossip(int step, Driver another) {
        if (canGossip(step, another)) {
            alreadyGossipDrivers.addAll(another.alreadyGossipDrivers);
            another.alreadyGossipDrivers.addAll(this.alreadyGossipDrivers);
        }
    }

    boolean canGossip(int step, Driver another) {
        return getStopByStep(step).equals(another.getStopByStep(step));
    }

    boolean allGossipped(int totalOfDrivers) {
        return alreadyGossipDrivers.size() == totalOfDrivers;
    }

    private String getStopByStep(int step) {
        return driverStops.get(step % driverStops.size());
    }

}