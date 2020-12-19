package tech.itpark.projectdelivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private long id;
    private long categoryId;
    private long vendorId;
    private String name;
    private String imageUrl;
    private int unitPrice;

    public Product(long id, long categoryId, long vendorId, String name, int unitPrice) {
        this.id = id;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
        this.name = name;
        this.unitPrice = unitPrice;
    }
}
