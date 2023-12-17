package loja.repository;

import loja.entity.CategoriaEntity;
import loja.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    Optional<ProdutoEntity> findByNome(String nome);
    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);
    List<ProdutoEntity> findByCategoria(CategoriaEntity categoriaEntity);

}
