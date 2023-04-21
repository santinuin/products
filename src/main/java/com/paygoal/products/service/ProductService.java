package com.paygoal.products.service;

import com.paygoal.products.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product create(Product product);
}
