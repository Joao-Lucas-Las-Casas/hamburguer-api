package br.com.codegrill.codegrill_api.services.mapers;

import br.com.codegrill.codegrill_api.dtos.ProductsDto;
import br.com.codegrill.codegrill_api.entities.ProductsEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductsMapper {
    public static ProductsEntity mapToProductsEntity(final ProductsDto productsDto) {
        return ProductsEntity.builder()
                .id(productsDto.id())
                .name(productsDto.name())
                .description(productsDto.description())
                .image(productsDto.image())
                .price(productsDto.price())
                .discount(productsDto.discount())
                .category(productsDto.category())
                .introduction(productsDto.introduction())
                .build();
    }
}
