package tech.itpark.projectdelivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
   private long id;
   private long customerId;
   private long vendorId;
   private long categoryId;
   private List<Integer> products;
   private List<Integer> qty;
   private int total;

   public Cart(long customerId, List<Integer> products, List<Integer> qty) {
      this.customerId = customerId;
      this.products = products;
      this.qty = qty;
   }
}
