package br.com.codegrill.codegrill_api.services;

import br.com.codegrill.codegrill_api.dtos.ProductsDto;
import br.com.codegrill.codegrill_api.entities.ProductsEntity;
import br.com.codegrill.codegrill_api.exceptions.NotFoundException;
import br.com.codegrill.codegrill_api.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.codegrill.codegrill_api.services.mapers.ProductsMapper.mapToProductsEntity;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Transactional
    public ProductsEntity save(final ProductsDto productsDto) {
        return productsRepository.save(mapToProductsEntity(productsDto));
    }

    public ProductsEntity findProductById(final Long id) {
        return productsRepository.findById(id).orElseThrow();
    }

    public Page<ProductsEntity> listProducts(final Pageable pageable) {
        final var productsEntities = productsRepository.findAll(pageable);
        productsEntities.getContent().stream().filter(productsEntity -> nonNull(productsEntity.getDiscount()) &&
                productsEntity.getDiscount().compareTo(BigDecimal.ZERO) > 0).forEach(productsEntity ->
                productsEntity.setPrice(
                        productsEntity.getPrice()
                                .multiply(BigDecimal.ONE
                                        .subtract(productsEntity.getDiscount()
                                                .divide(new BigDecimal(100), RoundingMode.FLOOR)))
                                .setScale(2, RoundingMode.HALF_UP)
                )
        );
        return productsEntities;
    }

    @Transactional
    public ProductsEntity updateProduct(final Long id, final ProductsDto productsDto) {
        final var productsEntity = productsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        return productsRepository.save(productsEntity.toBuilder()
                .name(productsDto.name())
                .description(productsDto.description())
                .image(productsDto.image())
                .price(productsDto.price())
                .discount(productsDto.discount())
                .build());
    }

    @Transactional
    public void deleteProduct(final Long id) {
        final var productsEntity = productsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        productsRepository.delete(productsEntity);
    }
}
