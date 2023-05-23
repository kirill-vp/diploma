package ru.javaops.topjava.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.Dish;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.Role;
import ru.javaops.topjava.repository.DishRepository;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.service.DishService;
import ru.javaops.topjava.to.DishTo;
import ru.javaops.topjava.util.DishUtil;
import ru.javaops.topjava.web.AuthUser;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.topjava.util.DishUtil.createTo;
import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api/restaurants/{restaurantId}/dishes";

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final DishService service;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {} for user {}", id, restaurantId, authUser.id());
        return ResponseEntity.of(repository.get(restaurantId, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId, @PathVariable int id) {
        if (authUser.hasRole(Role.ADMIN)) {
            log.info("delete {} for user {}", id, authUser.id());
            Dish dish = repository.getExistedOrBelonged(restaurantId, id);
            repository.delete(dish);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public List<DishTo> getAll(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        return repository.getAll(restaurantId).stream().map(dish -> createTo(dish)).collect(Collectors.toList());
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId,  @Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        if (authUser.hasRole(Role.ADMIN)) {
            int userId = authUser.id();
            log.info("update {} for restaurant {} for user {}", dishTo, restaurantId, userId);
            assureIdConsistent(dishTo, id);
            Dish dish = repository.getExistedOrBelonged(restaurantId, id);
            Dish updated = DishUtil.updateFromTo(dish, dishTo);
            service.save(restaurantId, updated);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo) {
        if (authUser.hasRole(Role.ADMIN)) {
            int userId = authUser.id();
            log.info("create {} for restaurant {} for user {}", dishTo, restaurantId, userId);
            checkNew(dishTo);
            Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
            Dish dish = new Dish(dishTo.getDishName(), dishTo.getDescription(), restaurant);
            Dish created = service.save(restaurantId, dish);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return new  ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }



}