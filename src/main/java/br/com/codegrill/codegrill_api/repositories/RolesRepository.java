package br.com.codegrill.codegrill_api.repositories;

import br.com.codegrill.codegrill_api.entities.RolesEntity;
import br.com.codegrill.codegrill_api.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByRoleName(final RoleNameEnum roleName);
}
