package br.com.fiap.satalert.repository;

import br.com.fiap.satalert.domain.entity.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegiaoRepository extends JpaRepository<Regiao, Long> {
    List<Regiao> findByEstado(String estado);
    List<Regiao> findByBioma(String bioma);
}
