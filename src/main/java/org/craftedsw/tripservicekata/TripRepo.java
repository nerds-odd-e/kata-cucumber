package org.craftedsw.tripservicekata;

import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.trip.TripDAO;
import org.craftedsw.tripservicekata.user.User;

import java.util.List;

public class TripRepo {
    public TripRepo() {
    }

    public List<Trip> findTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }
}