package loja.repository;

import loja.entity.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<VendaEntity, Long> {

    List<VendaEntity> findByStatusTrue();

}
