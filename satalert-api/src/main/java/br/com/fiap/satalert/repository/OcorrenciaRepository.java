package br.com.fiap.satalert.repository;

import br.com.fiap.satalert.domain.entity.Ocorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    Page<Ocorrencia> findByStatus(String status, Pageable pageable);
    List<Ocorrencia> findByRegiaoId(Long idRegiao);

    @Query("SELECT o FROM Ocorrencia o JOIN FETCH o.regiao JOIN FETCH o.alerta WHERE o.status = 'ATIVO' ORDER BY o.dtOcorrencia DESC")
    List<Ocorrencia> findAtivas();
}
