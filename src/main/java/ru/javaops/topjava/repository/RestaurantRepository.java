package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Meal> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.id")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Optional<Restaurant> get(int id);

}