package br.com.fiap.satalert.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_ALERTA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_ALERTA", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AlertaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alerta")
    @SequenceGenerator(name = "seq_alerta", sequenceName = "seq_alerta", allocationSize = 1)
    @Column(name = "id_alerta")
    private Long id;

    @Column(name = "ds_nivel_risco", nullable = false)
    private String nivelRisco = "BAIXO";

    @Column(name = "dt_deteccao", nullable = false)
    private LocalDate dtDeteccao = LocalDate.now();
}
