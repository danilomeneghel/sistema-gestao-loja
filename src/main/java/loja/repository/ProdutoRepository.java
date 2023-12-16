package loja.repository;

import loja.entity.CategoriaEntity;
import loja.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findByNomeContainingIgnoreCase(String nome);
    ProdutoEntity findByNome(String nome);
    List<ProdutoEntity> findByCategoria(CategoriaEntity categoriaEntity);

}
