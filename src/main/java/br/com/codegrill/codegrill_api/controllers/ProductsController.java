package br.com.codegrill.codegrill_api.controllers;

import br.com.codegrill.codegrill_api.dtos.ProductsDto;
import br.com.codegrill.codegrill_api.entities.ProductsEntity;
import br.com.codegrill.codegrill_api.services.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping
    public ResponseEntity<ProductsEntity> save(@RequestBody @Valid final ProductsDto productsDto,
                                               final UriComponentsBuilder uriBuilder) {
        final var productsEntity = productsService.save(productsDto);
        final var uri = uriBuilder.path("/v1/products/{id}").buildAndExpand(productsEntity.getId()).toUri();
        return ResponseEntity.created(uri).body(productsEntity);
    }

    @GetMapping
    public ResponseEntity<Page<ProductsEntity>> listProducts(
            @PageableDefault(sort = "category", direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(productsService.listProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsEntity> findProductById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(productsService.findProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsEntity> updateProduct(@PathVariable("id") final Long id,
                                                        @RequestBody final ProductsDto productsDto) {
        return ResponseEntity.ok(productsService.updateProduct(id, productsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") final Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
