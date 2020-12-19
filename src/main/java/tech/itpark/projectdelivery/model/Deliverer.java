package tech.itpark.projectdelivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Deliverer {
    private long id;
    private String name;
    private String location;
    private double lon;
    private double lat;

    public Deliverer(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
