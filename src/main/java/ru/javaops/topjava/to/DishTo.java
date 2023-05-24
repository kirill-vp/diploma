package ru.javaops.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {
    String dishName;
    String description;

    public DishTo(Integer id, String dishName, String description) {
        super(id);
        this.dishName = dishName;
        this.description = description;
    }
}
