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



@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "restaurant_name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 60)
    @NoHtml
    private String restaurantName;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String description;


    public Restaurant(Integer id, String restaurantName, String description) {
        super(id);
        this.restaurantName = restaurantName;
        this.description = description;
    }

}
