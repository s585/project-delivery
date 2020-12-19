package tech.itpark.projectdelivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vendor {
    private long id;
    private String name;
    private String address;
    private double lon;
    private double lat;

    public Vendor(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}


