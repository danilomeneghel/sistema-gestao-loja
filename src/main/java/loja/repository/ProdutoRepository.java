package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import loja.entity.ProdutoEntity;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);

}
