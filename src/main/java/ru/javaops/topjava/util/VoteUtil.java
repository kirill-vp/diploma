package ru.javaops.topjava.util;


import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.to.VoteTo;
import java.time.LocalDateTime;


@UtilityClass
public class VoteUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDateTime(), vote.getRestaurant());
    }
    public static Vote update(Vote vote, LocalDateTime localDateTime, Restaurant restaurant) {
        vote.setRestaurant(restaurant);
        vote.setDateTime(localDateTime);
        return vote;
    }

}
