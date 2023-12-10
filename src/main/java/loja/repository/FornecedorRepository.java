package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import loja.entity.FornecedorEntity;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    Optional<FornecedorEntity> findByNome(String nome);

    Optional<FornecedorEntity> findByNomeAndIdNot(String nome, Long id);

    Optional<FornecedorEntity> findByEmail(String email);

}
