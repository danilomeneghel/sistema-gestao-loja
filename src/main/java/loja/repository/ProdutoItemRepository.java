package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import loja.entity.ProdutoItemEntity;
import loja.entity.CategoriaEntity;

import java.util.List;

public interface ProdutoItemRepository extends JpaRepository<ProdutoItemEntity, Long> {

    List<ProdutoItemEntity> findByNomeContainingIgnoreCase(String nome);
    ProdutoItemEntity findByNome(String nome);
    List<ProdutoItemEntity> findByCategoria(CategoriaEntity categoriaEntity);

}
