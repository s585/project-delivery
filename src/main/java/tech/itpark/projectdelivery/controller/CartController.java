package tech.itpark.projectdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.projectdelivery.manager.CartManager;
import tech.itpark.projectdelivery.manager.OrderManager;
import tech.itpark.projectdelivery.model.Cart;
import tech.itpark.projectdelivery.model.Order;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartManager cartManager;
    private final OrderManager orderManager;

    @GetMapping
    public List<Cart> getAll() {return cartManager.getAll();}

    @GetMapping("/{id}")
    public Cart getById(@PathVariable long id) {
        return cartManager.getById(id);
    }

    @PostMapping
    public Cart save(@RequestBody Cart item) {
        return cartManager.save(item);
    }

    @PostMapping("/{id}/checkout")
    public Order checkOut(
            @PathVariable long id
    ) {
        return orderManager.checkOut(cartManager.getById(id));
    }

    @DeleteMapping("/{id}/remove")
    public Cart removeById(@PathVariable long id){
        return cartManager.removeById(id);
    }
}


