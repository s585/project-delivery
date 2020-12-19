package tech.itpark.projectdelivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.projectdelivery.manager.VendorManager;
import tech.itpark.projectdelivery.model.Vendor;

import java.util.List;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorManager vendorManager;

    @GetMapping
    public List<Vendor> getAll(){
        return vendorManager.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Vendor getById(@PathVariable long id){
        return vendorManager.getById(id);
    }

    @PostMapping
    public Vendor save(@RequestBody Vendor item)
    {
        return vendorManager.save(item);
    }

    @DeleteMapping("/{id}/remove")
    public Vendor removeById(@PathVariable long id){
        return vendorManager.removeById(id);
    }
}
