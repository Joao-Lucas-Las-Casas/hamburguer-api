package br.com.codegrill.codegrill_api.repositories;

import br.com.codegrill.codegrill_api.entities.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {
}
