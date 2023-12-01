package com.mvm.springdatajpaexample.model.product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product add(Product p)
    {
        return repo.save(p);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public boolean delete(int id)
    {
        try {
            repo.deleteById(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean update(int id, Product p)
    {
        Optional<Product> x = repo.findById(id);
        if (!x.isPresent()) return false;

        Product t = x.get();
        t.setName(p.getName());
        t.setColor(p.getColor());
        t.setPrice(p.getPrice());
        repo.save(t);
        return true;
    }
}
