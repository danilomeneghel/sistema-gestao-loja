package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import loja.entity.BairroEntity;
import loja.entity.MunicipioEntity;

import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<BairroEntity, Long> {

    List<BairroEntity> findByNomeContainingIgnoreCase(String nome);

    List<BairroEntity> findByMunicipio(MunicipioEntity municipio);

}
