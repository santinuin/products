package com.paygoal.products.service;

import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.exception.NameAlreadyExistsException;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    Product findById(Long id);

    Product findByName(String name);

    Product create(Product product) throws NameAlreadyExistsException;

    Product update(Long id, Product product) throws IdNotFoundException, NameAlreadyExistsException;

    void delete(Long id) throws IdNotFoundException;
}
