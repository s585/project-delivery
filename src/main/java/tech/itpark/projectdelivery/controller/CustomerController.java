package tech.itpark.projectdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.projectdelivery.manager.CustomerManager;
import tech.itpark.projectdelivery.model.Customer;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerManager customerManager;

    @GetMapping
    public List<Customer> getAll(){
        return customerManager.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Customer getById(@PathVariable long id){
        return customerManager.getById(id);
    }

    @PostMapping
    public Customer save(@RequestBody Customer item)
    {
        return customerManager.save(item);
    }

    @DeleteMapping("/{id}/remove")
    public Customer removeById(@PathVariable long id){
        return customerManager.removeById(id);
    }
}
