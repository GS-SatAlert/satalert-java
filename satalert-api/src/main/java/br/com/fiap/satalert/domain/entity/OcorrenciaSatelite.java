package br.com.fiap.satalert.domain.entity;

import br.com.fiap.satalert.domain.embedded.OcorrenciaSateliteId;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_OCORRENCIA_SATELITE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcorrenciaSatelite {

    @EmbeddedId
    private OcorrenciaSateliteId id;

    @ManyToOne
    @MapsId("idOcorrencia")
    @JoinColumn(name = "id_ocorrencia")
    private Ocorrencia ocorrencia;

    @ManyToOne
    @MapsId("idSatelite")
    @JoinColumn(name = "id_satelite")
    private Satelite satelite;

    @Column(name = "dt_passagem")
    private LocalDate dtPassagem = LocalDate.now();

    @Column(name = "nr_cobertura")
    private Double cobertura;
}
