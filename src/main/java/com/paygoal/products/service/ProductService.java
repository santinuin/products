package com.paygoal.products.service;

import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    Product create(Product product);

    Product update(Long id, Product product) throws IdNotFoundException;

    void delete(Long id) throws  IdNotFoundException;
}
