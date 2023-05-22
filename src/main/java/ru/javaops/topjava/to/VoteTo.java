package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.topjava.model.Restaurant;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDateTime dateTime;

    int restaurantId;

    public VoteTo(Integer id, LocalDateTime dateTime, int restaurantId) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
    }


}
