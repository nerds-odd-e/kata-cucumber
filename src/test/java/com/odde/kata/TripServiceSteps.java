package com.odde.kata;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.craftedsw.tripservicekata.TripRepo;
import org.craftedsw.tripservicekata.TripService_Original;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.List;

import static com.github.leeonky.dal.Assertions.expect;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripServiceSteps {

    private UserSession stubUserSession = mock(UserSession.class);
    private TripRepo stubTripRepo = mock(TripRepo.class);
    private TripService_Original tripService = new TripService_Original(stubUserSession, stubTripRepo);
    private Exception expectedException;
    private User user;
    private List<Trip> tripList;
    private User loggedUser = new User();

    @Given("logged user is null")
    public void loggedUserIsNull() {
        when(stubUserSession.getLoggedUser()).thenReturn(null);
    }

    @When("get trips by user")
    public void getTripsByUser() {
        try {
            tripList = tripService.getTripsByUser(user);
        } catch (UserNotLoggedInException e) {
            expectedException = e;
        }
    }

    @Then("got this error")
    public void gotThisError(String expression) {
        expect(expectedException).should(expression);
    }

    @Given("logged user is not null")
    public void loggedUserIsNotNull() {
        when(stubUserSession.getLoggedUser()).thenReturn(loggedUser);
    }

    @Then("trip list is")
    public void tripListIs(String expression) {
        expect(tripList).should(expression);
    }

    @And("user has no friends")
    public void userHasNoFriends() {
        user = new User();
    }

    @And("user has logged user as a friend")
    public void userHasLoggedUserAsAFriend() {
        user = new User();
        user.addFriend(loggedUser);
    }

    @And("user has a trip")
    public void userHasATrip() {
        when(stubTripRepo.findTripsByUser(eq(user))).thenReturn(List.of(new Trip()));
    }

    @And("user has some friends")
    public void userHasFriends() {
        user = new User();
        user.addFriend(new User());
    }
}
