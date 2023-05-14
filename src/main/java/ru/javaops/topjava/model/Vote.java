package ru.javaops.topjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava.util.validation.NoHtml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"user"})
public class Vote extends BaseEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Vote(Restaurant restaurant, User user, LocalDateTime dateTime) {
        this.restaurant = restaurant;
        this.user = user;
        this.dateTime = dateTime;
    }
    public Vote(Integer id, Restaurant restaurant, LocalDateTime dateTime) {
        this.id = id;
        this.restaurant = restaurant;
        this.dateTime = dateTime;
    }

    @Schema(hidden = true)
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    @Schema(hidden = true)
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
