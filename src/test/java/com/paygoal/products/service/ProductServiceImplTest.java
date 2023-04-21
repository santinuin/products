package com.paygoal.products.service;

import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.paygoal.products.data.LoadData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    ProductRepository repository;

    @Autowired
    ProductService service;

    List<Product> products;

    @BeforeEach
    void setUp() {

        products = List.of(crearProducto1().orElseThrow(),
                crearProducto2().orElseThrow(),
                crearProducto3().orElseThrow(),
                crearProducto4().orElseThrow(),
                crearProducto5().orElseThrow());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(products);

        List<Product> productList = service.findAll();

        verify(repository, times(1)).findAll();
        assertFalse(productList.isEmpty());
        assertEquals(5, productList.size());
    }

    @Test
    void findAllByOrderByPriceAsc() {
        List<Product> sortedProducts = List.of(crearProducto4().orElseThrow(),
                crearProducto3().orElseThrow(),
                crearProducto1().orElseThrow(),
                crearProducto5().orElseThrow(),
                crearProducto2().orElseThrow());

        when(repository.findAllByOrderByPriceAsc()).thenReturn(sortedProducts);

        List<Product> productList = service.findAllByOrderByPriceAsc();

        verify(repository, times(1)).findAllByOrderByPriceAsc();
        assertFalse(productList.isEmpty());
        assertEquals(5, productList.size());
    }

    @Test
    void findAllByOrderByPriceDesc() {
        List<Product> sortedProducts = List.of(crearProducto2().orElseThrow(),
                crearProducto5().orElseThrow(),
                crearProducto1().orElseThrow(),
                crearProducto3().orElseThrow(),
                crearProducto4().orElseThrow());

        when(repository.findAllByOrderByPriceDesc()).thenReturn(sortedProducts);

        List<Product> productList = service.findAllByOrderByPriceDesc();

        verify(repository, times(1)).findAllByOrderByPriceDesc();
        assertFalse(productList.isEmpty());
        assertEquals(5, productList.size());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));

        Product product = service.findById(1L);

        verify(repository, times(1)).findById(anyLong());
        assertNotNull(product);
    }

    @Test
    void findByName() {
        when(repository.findByNameIgnoreCase("Escritorio")).thenReturn(products.get(3));

        Product product = service.findByName("Escritorio");

        verify(repository, times(1)).findByNameIgnoreCase(anyString());
        assertNotNull(product);
    }

    @Test
    void create() {
        Product newProduct = new Product(6L, "Celular", "El mejor celular del mundo", new BigDecimal("150000.00"), 5);
        when(repository.save(newProduct))
                .thenReturn(newProduct);

        Product product = service.create(newProduct);

        verify(repository, times(1)).save(any(Product.class));
        assertEquals(product, newProduct);
    }

    @Test
    void update() throws IdNotFoundException {
        Product updatedProduct = new Product(2L, "Smart TV", "Tv con calidad Full HD", new BigDecimal("120000.99"), 7);
        when(repository.findById(2L)).thenReturn(Optional.ofNullable(products.get(1)));
        when(repository.save(products.get(1))).thenReturn(updatedProduct);

        Product update = service.update(2L, updatedProduct);

        verify(repository, times(1)).save(any(Product.class));
        assertEquals(updatedProduct, update);
    }

    @Test
    void delete() throws IdNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));

        service.delete(1L);

        verify(repository, times(1)).deleteById(anyLong());
    }
}