package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo {

    String restaurantName;

    String description;

    public RestaurantTo(Integer id, String restaurantName, String description) {
        super(id);
        this.restaurantName = restaurantName;
        this.description = description;
    }
}
