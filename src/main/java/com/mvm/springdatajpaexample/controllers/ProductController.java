package com.mvm.springdatajpaexample.controllers;

import com.mvm.springdatajpaexample.model.product.Product;
import com.mvm.springdatajpaexample.model.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public List<Product> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Product addProduct(@RequestBody Product product)
    {
        return service.add(product);
    }

    @PutMapping("/{id}")
    public boolean updateProduct(@PathVariable("id") int id, @RequestBody Product product)
    {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable("id") int id)
    {
        return service.delete(id);
    }

}
