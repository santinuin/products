package com.paygoal.products.data;

import com.paygoal.products.business.dto.ProductDto;
import com.paygoal.products.domain.Product;

import java.math.BigDecimal;
import java.util.Optional;

public class LoadData {

    //ENTITY
    public static Optional<Product> crearProducto1(){
        return Optional.of(new Product(1L, "Bicicleta", "Las mejores bicis del mercado", new BigDecimal("70000.00"), 40));
    }

    public static Optional<Product> crearProducto2(){
        return Optional.of(new Product(2L, "Smart TV", "Tv con calidad Full HD", new BigDecimal("90000.99"), 20));
    }

    public static Optional<Product> crearProducto3(){
        return Optional.of(new Product(3L, "Sillón", "Cubierto en cuero, máximo confort", new BigDecimal("65000.00"), 17));
    }

    public static Optional<Product> crearProducto4(){
        return Optional.of(new Product(4L, "Escritorio", "Escritorio moderno y funcional", new BigDecimal("18000.00"), 12));
    }

    public static Optional<Product> crearProducto5(){
        return Optional.of(new Product(5L, "Sommier", "Comodo, durable y firme", new BigDecimal("72000.80"), 5));
    }

    //DTO
    public static Optional<ProductDto> crearProductoDto1(){
        return Optional.of(new ProductDto(1L, "Bicicleta", "Las mejores bicis del mercado", new BigDecimal("70000.00"), 40));
    }

    public static Optional<ProductDto> crearProductoDto2(){
        return Optional.of(new ProductDto(2L, "Smart TV", "Tv con calidad Full HD", new BigDecimal("90000.99"), 20));
    }

    public static Optional<ProductDto> crearProductoDto3(){
        return Optional.of(new ProductDto(3L, "Sillón", "Cubierto en cuero, máximo confort", new BigDecimal("65000.00"), 17));
    }

    public static Optional<ProductDto> crearProductoDto4(){
        return Optional.of(new ProductDto(4L, "Escritorio", "Escritorio moderno y funcional", new BigDecimal("18000.00"), 12));
    }

    public static Optional<ProductDto> crearProductoDto5(){
        return Optional.of(new ProductDto(5L, "Sommier", "Comodo, durable y firme", new BigDecimal("72000.80"), 5));
    }
}