package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.DataConflictException;
import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId ORDER BY v.dateTime DESC")
    List<Vote> getAll(int userId);

    @Query("SELECT v from Vote v WHERE v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime DESC")
    List<Vote> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT v FROM Vote v WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> get(int userId, int id);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> getWithUser(int id, int userId);

    default Vote getExistedOrBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + "   is not exist or doesn't belong to User id=" + userId));
    }
}