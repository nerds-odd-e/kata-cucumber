package org.craftedsw.tripservicekata;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TripService_Original {

	private final UserSession userSession;
	private final TripRepo tripRepo;

	public TripService_Original() {
		this(UserSession.getInstance(), new TripRepo());
	}

	public TripService_Original(UserSession userSession, TripRepo tripRepo) {
		this.userSession = userSession;
		this.tripRepo = tripRepo;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		return getLoggedUser().orElseThrow(UserNotLoggedInException::new)
				.isFanOfUser(user)
				.map(friend -> tripRepo.findTripsByUser(user))
				.orElse(new ArrayList<>());
	}

	private Optional<User> getLoggedUser() {
		return Optional.ofNullable(userSession.getLoggedUser());
	}

}
