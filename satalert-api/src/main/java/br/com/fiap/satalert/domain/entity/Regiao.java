package br.com.fiap.satalert.domain.entity;

import br.com.fiap.satalert.domain.embedded.Coordenada;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_REGIAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_regiao")
    @SequenceGenerator(name = "seq_regiao", sequenceName = "seq_regiao", allocationSize = 1)
    @Column(name = "id_regiao")
    private Long id;

    @Column(name = "nm_regiao", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "ds_bioma", length = 50)
    private String bioma;

    @Embedded
    private Coordenada coordenada;

    @Column(name = "dt_cadastro", nullable = false)
    private LocalDate dtCadastro = LocalDate.now();

    @OneToMany(mappedBy = "regiao", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Ocorrencia> ocorrencias;
}
