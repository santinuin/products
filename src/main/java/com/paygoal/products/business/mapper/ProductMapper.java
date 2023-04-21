package com.paygoal.products.business.mapper;

import com.paygoal.products.business.dto.ProductDto;
import com.paygoal.products.domain.Product;

public interface ProductMapper {
    Product toEntity(ProductDto productDTO);

    ProductDto toDto(Product product);

    Product partialUpdate(ProductDto productDTO, Product product);
}
