package ru.javaops.topjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends BaseEntity {
    @Column(name = "dish_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 120)
    private String dishName;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 220)
    @NoHtml
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String dishName, String description) {
        super(id);
        this.dishName = dishName;
        this.description = description;
    }
    public Dish(String dishName, String description, Restaurant restaurant) {
        this.dishName = dishName;
        this.description = description;
        this.restaurant = restaurant;

    }
}
