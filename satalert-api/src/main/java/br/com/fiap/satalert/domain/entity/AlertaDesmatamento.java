package br.com.fiap.satalert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("DESMATAMENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDesmatamento extends AlertaBase {

    @Column(name = "nr_area_ha")
    private Double areaHectares;

    @Column(name = "nr_cobertura")
    private Double coberturaVegetal;
}
