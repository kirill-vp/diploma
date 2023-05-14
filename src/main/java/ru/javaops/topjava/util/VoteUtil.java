package ru.javaops.topjava.util;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.service.RestaurantService;
import ru.javaops.topjava.to.MealTo;
import ru.javaops.topjava.to.ResultTo;
import ru.javaops.topjava.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {

    private final RestaurantService restaurantService = null;

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDateTime(), vote.getRestaurant());
    }
    public static Vote update(Vote vote, LocalDateTime localDateTime, Restaurant restaurant) {
        vote.setRestaurant(restaurant);
        vote.setDateTime(localDateTime);
        return vote;
    }




    public static Map<Restaurant, Long> calculateResultsForPeriod(Collection<Vote> votes, Predicate<Vote> filter) {
        Map<Restaurant, Long> votesSumByRestaurant = votes.stream()
                .filter(filter)
                .collect(
                        Collectors.groupingBy(Vote::getRestaurant, Collectors.counting())

                );
        return votesSumByRestaurant;
    }
}
