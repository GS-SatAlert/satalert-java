package br.com.fiap.satalert.domain.embedded;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordenada {

    @Column(name = "nr_latitude", nullable = false)
    private Double latitude;

    @Column(name = "nr_longitude", nullable = false)
    private Double longitude;
}
