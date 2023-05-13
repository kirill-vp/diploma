package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ResultTo extends BaseTo {
    int restaurantId;
    int countOfVotes;

    public ResultTo(Integer id, int restaurantId, int countOfVotes) {
        super(id);
        this.restaurantId = restaurantId;
        this.countOfVotes = countOfVotes;
    }
}
