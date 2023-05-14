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
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.service.RestaurantService;
import ru.javaops.topjava.to.RestaurantTo;
import ru.javaops.topjava.web.AuthUser;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.topjava.util.RestaurantUtil.createTo;
import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    //private final MealRepository repository;
    private final RestaurantRepository repository;
    private final RestaurantService service;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get restaurant {} for user {}", id, authUser.id());
        return ResponseEntity.of(repository.get(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete {} for user {}", id, authUser.id());
        Restaurant restaurant = repository.getExisted(id);
        repository.delete(restaurant);
    }

    @GetMapping
    public List<RestaurantTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user {}", authUser.id());
        return repository.getAll().stream().map(restaurant -> createTo(restaurant)).collect(Collectors.toList());
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        int userId = authUser.id();
        log.info("update {} for user {}", restaurant, userId);
        assureIdConsistent(restaurant, id);
        service.save(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Restaurant restaurant) {
        int userId = authUser.id();
        log.info("create {} for user {}", restaurant, userId);
        checkNew(restaurant);
        Restaurant created = service.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}