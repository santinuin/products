package com.paygoal.products.service;

import com.paygoal.products.domain.Product;
import com.paygoal.products.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return repository.save(product);
    }
}
