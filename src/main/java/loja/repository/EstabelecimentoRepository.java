package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import loja.entity.EstabelecimentoEntity;

import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<EstabelecimentoEntity, Long> {

    Optional<EstabelecimentoEntity> findByNome(String nome);

    Optional<EstabelecimentoEntity> findByEmail(String email);

}
