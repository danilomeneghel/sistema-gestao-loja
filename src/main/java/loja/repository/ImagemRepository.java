package loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import loja.entity.ImagemEntity;

public interface ImagemRepository extends JpaRepository<ImagemEntity, Long> {

}
