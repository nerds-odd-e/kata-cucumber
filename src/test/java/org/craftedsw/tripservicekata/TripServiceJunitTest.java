package org.craftedsw.tripservicekata;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TripServiceJunitTest {
    private final UserSession userSession = mock(UserSession.class);
    private final TripRepo tripRepo = mock(TripRepo.class);
    private final TripServiceJunit tripServiceJunit = new TripServiceJunit(userSession, tripRepo);

    @Nested
    class NoLogin {

        @BeforeEach
        void noLogin() {
            when(userSession.getLoggedUser()).thenReturn(null);
        }

        @Test
        void should_raise_error() {
            assertThatThrownBy(() -> tripServiceJunit.getTripsByUser(new User()))
                    .isInstanceOf(UserNotLoggedInException.class);
        }
    }

    @Nested
    class Login {
        User loginUser = new User();

        @BeforeEach
        void login() {
            when(userSession.getLoggedUser()).thenReturn(loginUser);
        }

        @Nested
        class InputUserHasNoFriend {

            @Test
            void should_return_empty() {
                assertThat(tripServiceJunit.getTripsByUser(new User())).isEmpty();
            }
        }

        @Nested
        class InputUserHasFriends {
            User inputUser = new User();

            @Nested
            class LoginUserIsNotFriendOfInputUser {

                @BeforeEach
                void givenNotLoginUserFriend() {
                    givenFriendOfInput(new User());
                }

                @Test
                void should_return_empty() {
                    givenNotLoginUserFriend();
                    assertThat(tripServiceJunit.getTripsByUser(inputUser)).isEmpty();
                }
            }

            @Nested
            class LoginUserIsFriendOfInputUser {

                @BeforeEach
                void setUpFriend() {
                    givenFriendOfInput(loginUser);
                }

                @Test
                void should_return_input_users_trips() {
                    Trip trip = new Trip();
                    givenInputUserTrip(trip);

                    assertThat(tripServiceJunit.getTripsByUser(inputUser)).contains(trip);
                }

                private void givenInputUserTrip(Trip... trips) {
                    when(tripRepo.findTripsByUser(eq(inputUser))).thenReturn(asList(trips));
                }
            }

            private void givenFriendOfInput(User user) {
                inputUser.addFriend(user);
            }
        }
    }
}