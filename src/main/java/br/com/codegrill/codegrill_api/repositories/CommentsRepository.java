package br.com.codegrill.codegrill_api.repositories;

import br.com.codegrill.codegrill_api.entities.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
}
