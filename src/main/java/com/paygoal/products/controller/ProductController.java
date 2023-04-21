package com.paygoal.products.controller;

import com.paygoal.products.business.dto.ProductDto;
import com.paygoal.products.business.mapper.ProductMapper;
import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductController {

    private final ProductService service;

    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {

        List<ProductDto> productDtoList = this.service.findAll()
                .stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);

    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto) {

        Map<String, Object> response = new HashMap<>();

        Product newProduct = this.service.create(this.mapper.toEntity(productDto));

        response.put("message", "El producto ha sido creado con éxito");
        response.put("product", newProduct);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody ProductDto productDto) throws IdNotFoundException {

        Map<String, Object> response = new HashMap<>();

        Product updatedProduct = this.service.update(id, this.mapper.toEntity(productDto));

        response.put("message", "El producto ID: " + updatedProduct.getId() + " ha sido modificado con éxito");
        response.put("product", updatedProduct);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IdNotFoundException {

        Map<String, Object> response = new HashMap<>();

        this.service.delete(id);

        response.put("message", "El producto ID: " + id + " ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
