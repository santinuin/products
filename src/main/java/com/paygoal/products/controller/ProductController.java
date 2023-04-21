package com.paygoal.products.controller;

import com.paygoal.products.business.dto.ProductDto;
import com.paygoal.products.business.mapper.ProductMapper;
import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.exception.NameAlreadyExistsException;
import com.paygoal.products.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Product Controller", tags = "Acciones permitidas para productos")
public class ProductController {

    private final ProductService service;

    private final ProductMapper mapper;

    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @ApiOperation(value = "Listar todos los productos",
            notes = "Lista todos los productos, si queremos ordenarlos por precio debemos agregar un parametro booleano," +
                    " en donde si es true, lo hace de forma ascendente, y si es false lo hace de forma descendente")
    public ResponseEntity<?> list(@RequestParam(required = false) Boolean orderedByPrice) {

        if (orderedByPrice == null) {
            List<ProductDto> productDtoList = this.service.findAll()
                    .stream()
                    .map(this.mapper::toDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        }

        if (orderedByPrice) {
            List<ProductDto> productDtoList = this.service.findAllByOrderByPriceAsc()
                    .stream()
                    .map(this.mapper::toDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        }

        List<ProductDto> productDtoList = this.service.findAllByOrderByPriceDesc()
                .stream()
                .map(this.mapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);

    }

    @GetMapping("/buscar")
    @ApiOperation(value = "Buscar productos",
            notes = "Podemos buscar por ID o por Nombre, siempre que se lo especifiquemos en los parametros," +
                    "(siempre va a poderar ID por sobre el nombre si se especifican ambos)")
    public ResponseEntity<?> findByIdOrName(@RequestParam(required = false) Long id,
                                            @RequestParam(required = false) String name) {
        if (id != null) {
            ProductDto productDto = this.mapper.toDto(this.service.findById(id));

            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }

        ProductDto productDto = this.mapper.toDto(this.service.findByName(name));

        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/crear")
    @ApiOperation(value = "Crear producto nuevo")
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto) throws NameAlreadyExistsException {

        Map<String, Object> response = new HashMap<>();

        Product newProduct = this.service.create(this.mapper.toEntity(productDto));

        response.put("message", "El producto ha sido creado con éxito");
        response.put("product", newProduct);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Modificar producto")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody ProductDto productDto) throws IdNotFoundException {

        Map<String, Object> response = new HashMap<>();

        Product updatedProduct = this.service.update(id, this.mapper.toEntity(productDto));

        response.put("message", "El producto ID: " + updatedProduct.getId() + " ha sido modificado con éxito");
        response.put("product", updatedProduct);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    @ApiOperation(value = "Eliminar producto")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws IdNotFoundException {

        Map<String, Object> response = new HashMap<>();

        this.service.delete(id);

        response.put("message", "El producto ID: " + id + " ha sido eliminado con éxito");

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
