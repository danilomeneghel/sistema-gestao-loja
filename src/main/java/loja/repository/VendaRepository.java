package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import loja.entity.VendaEntity;

import java.util.List;

public interface VendaRepository extends JpaRepository<VendaEntity, Long> {

    List<VendaEntity> findByStatusTrue();

}
