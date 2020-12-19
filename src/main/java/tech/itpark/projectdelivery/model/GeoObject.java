package tech.itpark.projectdelivery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@NoArgsConstructor
@Data
public class GeoObject {
    private String name;
    private String point;
}

