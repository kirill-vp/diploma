package ru.javaops.topjava.web.vote;

import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.service.RestaurantService;
import ru.javaops.topjava.to.MealTo;
import ru.javaops.topjava.to.VoteTo;
import ru.javaops.topjava.web.MatcherFactory;

import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.LocalDateTime.of;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant");
    public static MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;
    public static final int ADMIN_VOTE_ID = 8;

    public static final Restaurant RESTAURANT1 = new Restaurant(1, "Fast Food Rest", "Fast Food Restauran: burgers, french fries, etc.");
    public static final Restaurant RESTAURANT2 = new Restaurant(2, "Itailian Causine Rest", "Itailian Causine Restauant: pizza, etc");
    public static final Restaurant RESTAURANT3 = new Restaurant(3, "Vegan Rest", "Vegan Restaurant: dishes without meat");

    public static final Vote vote1 = new Vote(VOTE1_ID, RESTAURANT1, of(2020, Month.JANUARY, 30, 10, 0));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, RESTAURANT1, of(2020, Month.FEBRUARY, 20, 13, 0));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, RESTAURANT1, of(2020, Month.MARCH, 30, 20, 0));
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, RESTAURANT1, of(2020, Month.APRIL, 30, 0, 0));
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, RESTAURANT1, of(2020, Month.MAY, 31, 10, 0));
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, RESTAURANT1, of(2020, Month.JUNE, 30, 13, 0));
    public static final Vote vote7 = new Vote(VOTE1_ID + 6, RESTAURANT2, of(2020, Month.JULY, 31, 20, 0));

    public static final Vote adminVote1 = new Vote(ADMIN_VOTE_ID, RESTAURANT1, of(2020, Month.JANUARY, 31, 14, 0));

    public static final Vote adminVote2 = new Vote(ADMIN_VOTE_ID + 1, RESTAURANT2, of(2020, Month.FEBRUARY, 20, 21, 0));

    public static final List<Vote> votes = List.of(vote7, vote6, vote5, vote4, vote3, vote2, vote1);

    public static Vote getNew() {
        return new Vote(null, RESTAURANT1, of(2020, Month.FEBRUARY, 1, 18, 0));
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, RESTAURANT3, vote1.getDateTime().plus(2, ChronoUnit.MINUTES));
    }
}
