package tech.itpark.projectdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.projectdelivery.manager.OrderManager;
import tech.itpark.projectdelivery.model.Order;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderManager manager;

    @GetMapping
    public List<Order> getAll(){
        return manager.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Order getById(@PathVariable long id){
        return manager.getById(id);
    }

    @GetMapping("/customers/{customerId}")
    public List<Order> getByCustomerId(@PathVariable long customerId){
        return manager.getByCustomerId(customerId);
    }

    @GetMapping("/vendors/{vendorId}")
    public List<Order> getByVendorId(@PathVariable long vendorId){
        return manager.getByVendorId(vendorId);
    }

    @GetMapping("/search")
    public List<Order> search(@RequestParam long delivererId, @RequestParam String date1, @RequestParam String date2){
        return manager.search(delivererId, date1, date2);
    }

    @DeleteMapping("/{id}/remove")
    public Order removeById(@PathVariable long id){
        return manager.removeById(id);
    }
}
