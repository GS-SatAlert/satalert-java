package br.com.fiap.satalert.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("QUEIMADA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AlertaQueimada extends AlertaBase {

    @Column(name = "nr_temperatura")
    private Double temperatura;

    @Column(name = "nr_fumaca")
    private Double nivelFumaca;
}
