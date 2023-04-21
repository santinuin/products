package com.paygoal.products.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paygoal.products.business.dto.ProductDto;
import com.paygoal.products.business.mapper.ProductMapper;
import com.paygoal.products.data.LoadData;
import com.paygoal.products.domain.Product;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.paygoal.products.data.LoadData.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ProductService service;

    @MockBean
    ProductMapper productMapper;

    ObjectMapper mapper;
    List<Product> productList;
    List<ProductDto> productDtoList;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();

        productList = List.of(crearProducto1().orElseThrow(),
                crearProducto2().orElseThrow(),
                crearProducto3().orElseThrow(),
                crearProducto4().orElseThrow(),
                crearProducto5().orElseThrow());

        productDtoList = List.of(crearProductoDto1().orElseThrow(),
                crearProductoDto2().orElseThrow(),
                crearProductoDto3().orElseThrow(),
                crearProductoDto4().orElseThrow(),
                crearProductoDto5().orElseThrow());
    }

    @Test
    void list() throws Exception {
        when(service.findAll()).thenReturn(productList);
        when(productMapper.toDto(productList.get(0))).thenReturn(productDtoList.get(0));
        when(productMapper.toDto(productList.get(1))).thenReturn(productDtoList.get(1));
        when(productMapper.toDto(productList.get(2))).thenReturn(productDtoList.get(2));
        when(productMapper.toDto(productList.get(3))).thenReturn(productDtoList.get(3));
        when(productMapper.toDto(productList.get(4))).thenReturn(productDtoList.get(4));

        mvc.perform(get("/productos").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(content().json(mapper.writeValueAsString(productList)));
        verify(service).findAll();
        verify(productMapper, times(5)).toDto(any(Product.class));
    }

    @Test
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(productList.get(0));
        when(productMapper.toDto(productList.get(0))).thenReturn(productDtoList.get(0));

        mvc.perform(get("/productos/buscar").contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))


                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(productDtoList.get(0))));
        verify(service).findById(anyLong());
        verify(productMapper, times(1)).toDto(any(Product.class));

    }

    @Test
    void create() throws Exception {
        Product product = new Product(6L, "Celular", "El mejor celular del mundo", new BigDecimal("150000.00"), 5);
        ProductDto productDto = new ProductDto(6L, "Celular", "El mejor celular del mundo", new BigDecimal("150000.00"), 5);
        when(productMapper.toEntity(productDto)).thenReturn(product);
        when(service.create(product)).thenReturn(product);

        mvc.perform(post("/productos/crear").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDto)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto ha sido creado con éxito"))
                .andExpect(jsonPath("$.product.id").value(6))
                .andExpect(jsonPath("$.product.name").value("Celular"))
                .andExpect(jsonPath("$.product.description").value("El mejor celular del mundo"))
                .andExpect(jsonPath("$.product.price").value("150000.0"))
                .andExpect(jsonPath("$.product.quantity").value(5));
        verify(service).create(any());
        verify(productMapper).toEntity(any());
    }

    @Test
    void update() throws Exception {
        Long id = 2L;
        ProductDto updatedProductDto = new ProductDto(id, "Smart TV", "Tv con calidad Full HD", new BigDecimal("120000.99"), 7);
        Product updatedProduct = new Product(id, "Smart TV", "Tv con calidad Full HD", new BigDecimal("120000.99"), 7);
        when(productMapper.toEntity(updatedProductDto)).thenReturn(updatedProduct);
        when(service.update(2L, updatedProduct)).thenReturn(updatedProduct);

        mvc.perform(put("/productos/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedProductDto)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto ID: " + id + " ha sido modificado con éxito"))
                .andExpect(jsonPath("$.product.price").value("120000.99"))
                .andExpect(jsonPath("$.product.quantity").value(7));
        verify(service).update(anyLong(), any());
        verify(productMapper).toEntity(any());

    }

    @Test
    void deleteById() throws Exception {
        Long id = 5L;
        mvc.perform(delete("/productos/eliminar/{id}", id).contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto ID: " + id + " ha sido eliminado con éxito"));

        verify(service).delete(anyLong());
    }
}