package tech.itpark.projectdelivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private long id;
    private long delivererId;
    private long customerId;
    private Date date;
    private List<Integer> products;
    private List<Integer> qty;
    private int deliveryPrice;
    private int total;
    private int status;

}



