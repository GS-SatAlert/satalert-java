package br.com.fiap.satalert.repository;

import br.com.fiap.satalert.domain.entity.AlertaBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AlertaRepository extends JpaRepository<AlertaBase, Long> {
    List<AlertaBase> findByNivelRisco(String nivelRisco);

    @Query("SELECT a FROM AlertaBase a WHERE TYPE(a) = AlertaQueimada AND a.nivelRisco = :nivel")
    List<AlertaBase> findQueimadaPorNivel(@Param("nivel") String nivel);
}
