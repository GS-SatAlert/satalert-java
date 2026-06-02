package br.com.fiap.satalert.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_OCORRENCIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ocorrencia")
    @SequenceGenerator(name = "seq_ocorrencia", sequenceName = "seq_ocorrencia", allocationSize = 1)
    @Column(name = "id_ocorrencia")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alerta", nullable = false)
    private AlertaBase alerta;

    @Column(name = "dt_ocorrencia", nullable = false)
    private LocalDate dtOcorrencia = LocalDate.now();

    @Column(name = "dt_resolucao")
    private LocalDate dtResolucao;

    @Column(name = "ds_status", nullable = false, length = 20)
    private String status = "ATIVO";

    @Column(name = "ds_observacao", length = 500)
    private String observacao;

    @OneToMany(mappedBy = "ocorrencia", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<OcorrenciaSatelite> satelites;
}
