package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import loja.entity.CategoriaEntity;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    List<CategoriaEntity> findByNomeContainingIgnoreCase(String nome);

}
