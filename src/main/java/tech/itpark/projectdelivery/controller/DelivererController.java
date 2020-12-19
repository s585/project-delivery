package tech.itpark.projectdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.projectdelivery.manager.DelivererManager;
import tech.itpark.projectdelivery.model.Deliverer;

import java.util.List;

@RestController
@RequestMapping("/deliverers")
@RequiredArgsConstructor
public class DelivererController {
    private final DelivererManager delivererManager;

    @GetMapping
    public List<Deliverer> getAll(){
        return delivererManager.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Deliverer getById(@PathVariable long id){
        return delivererManager.getById(id);
    }

    @PostMapping
    public Deliverer save(@RequestBody Deliverer item)
    {
        return delivererManager.save(item);
    }

    @DeleteMapping("/{id}/remove")
    public Deliverer removeById(@PathVariable long id){
        return delivererManager.removeById(id);
    }
}