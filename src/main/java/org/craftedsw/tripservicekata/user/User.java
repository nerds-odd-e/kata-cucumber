package org.craftedsw.tripservicekata.user;

import org.craftedsw.tripservicekata.trip.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User {

	private List<Trip> trips = new ArrayList<>();
	private List<User> friends = new ArrayList<>();

	public List<User> getFriends() {
		return friends;
	}

	public void addFriend(User user) {
		friends.add(user);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}

	public List<Trip> trips() {
		return trips;
	}

	public Optional<User> isFanOfUser(User user) {
//		return when(user.friends.stream().anyMatch(friend -> friend.equals(this))).optional(() -> user);
		return user.friends.stream().anyMatch(friend -> friend.equals(this)) ? Optional.of(user) : Optional.empty();
	}
}
