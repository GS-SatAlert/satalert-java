package br.com.fiap.satalert.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_SATELITE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Satelite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_satelite")
    @SequenceGenerator(name = "seq_satelite", sequenceName = "seq_satelite", allocationSize = 1)
    @Column(name = "id_satelite")
    private Long id;

    @Column(name = "nm_satelite", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_orbita", nullable = false, length = 50)
    private String orbita;

    @Column(name = "nm_operadora", nullable = false, length = 100)
    private String operadora;

    @Column(name = "nr_altitude")
    private Double altitude;

    @Column(name = "dt_lancamento")
    private LocalDate dtLancamento;
}
