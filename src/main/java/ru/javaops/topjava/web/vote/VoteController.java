package ru.javaops.topjava.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.User;
import ru.javaops.topjava.model.Vote;
import ru.javaops.topjava.repository.RestaurantRepository;
import ru.javaops.topjava.repository.VoteRepository;
import ru.javaops.topjava.service.RestaurantService;
import ru.javaops.topjava.service.VoteService;
import ru.javaops.topjava.to.ResultTo;
import ru.javaops.topjava.to.VoteTo;
import ru.javaops.topjava.util.MealsUtil;
import ru.javaops.topjava.util.UsersUtil;
import ru.javaops.topjava.util.VoteUtil;
import ru.javaops.topjava.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javaops.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javaops.topjava.util.VoteUtil.createTo;
import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/votes";

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final VoteService service;


    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        log.info("get vote {} for user {}", id, userId);
        return ResponseEntity.of(repository.get(userId, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        log.info("delete {} for user {}", id, userId);
        Vote vote = repository.getExistedOrBelonged(authUser.id(), id);
        repository.delete(vote);
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("getAll for user {}", userId);
        return repository.getAll(userId).stream().map(vote -> createTo(vote)).collect(Collectors.toList());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo, @PathVariable int id) {
        int userId = authUser.id();
        if (voteTo.getDateTime().isAfter(LocalDate.now().atStartOfDay()) && voteTo.getDateTime().isBefore(LocalDate.now().atTime(11, 0, 0))) {
            log.info("update {} for user {}", voteTo, userId);
            assureIdConsistent(voteTo, id);
            Vote vote = repository.getExistedOrBelonged(userId, id);
            Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
            service.save(userId, VoteUtil.update(vote,voteTo.getDateTime(),restaurant));
        }
        log.info("it's too late for update {} for user {}", voteTo, userId);


    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo) {
        int userId = authUser.id();
        log.info("create {} for user {}", voteTo, userId);
        checkNew(voteTo);
        Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
        User user = authUser.getUser();
        Vote vote = new Vote(restaurant, user, voteTo.getDateTime());
        Vote created = service.save(userId, vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}