package br.com.fiap.satalert.domain.embedded;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcorrenciaSateliteId implements Serializable {

    @Column(name = "id_ocorrencia")
    private Long idOcorrencia;

    @Column(name = "id_satelite")
    private Long idSatelite;
}
