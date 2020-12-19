package tech.itpark.projectdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.projectdelivery.manager.ProductManager;
import tech.itpark.projectdelivery.model.Product;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductManager productManager;

    @GetMapping
    public List<Product> getAll(){
        return productManager.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Product getById(@PathVariable long id){
        return productManager.getById(id);
    }

    @PostMapping
    public Product save(@RequestBody Product item)
    {
        return productManager.save(item);
    }

    @DeleteMapping("/{id}/remove")
    public Product removeById(@PathVariable long id){
        return productManager.removeById(id);
    }
}
