package com.paygoal.products.service;

import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
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
    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product update(Long id, Product product) throws IdNotFoundException {

        Product productToUpdate = this.repository.findById(id).orElse(null);

        if(productToUpdate == null){
            throw new IdNotFoundException("Error: no se pudo editar, el producto ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setQuantity(product.getQuantity());

        return this.repository.save(productToUpdate);
    }
}
