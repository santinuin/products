package com.paygoal.products.service;

import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllByOrderByPriceAsc() {
        return this.repository.findAllByOrderByPriceAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllByOrderByPriceDesc() {
        return this.repository.findAllByOrderByPriceDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Product findByName(String name) {
        return this.repository.findByNameIgnoreCase(name);
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return this.repository.save(product);
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) throws IdNotFoundException {

        Product productToUpdate = this.repository.findById(id).orElse(null);

        if (productToUpdate == null) {
            throw new IdNotFoundException("Error: no se pudo editar, el producto ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setQuantity(product.getQuantity());

        return this.repository.save(productToUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IdNotFoundException {

        Product productToDelete = this.repository.findById(id).orElse(null);

        if (productToDelete == null) {
            throw new IdNotFoundException("Error: no se pudo eliminar, el producto ID: "
                    .concat(id.toString().concat(" no existe.")));
        }

        this.repository.deleteById(id);
    }

}
