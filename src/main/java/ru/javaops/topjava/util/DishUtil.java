package ru.javaops.topjava.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava.model.Dish;
import ru.javaops.topjava.to.DishTo;


@UtilityClass
public class DishUtil {

    public static DishTo createTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getDishName(), dish.getDescription());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setDishName(dishTo.getDishName());
        dish.setDescription(dishTo.getDescription());
        return dish;
    }
}
