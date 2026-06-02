package br.com.fiap.satalert.service;

import br.com.fiap.satalert.domain.entity.*;
import br.com.fiap.satalert.dto.*;
import br.com.fiap.satalert.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final RegiaoRepository regiaoRepository;

    @Transactional
    public OcorrenciaResponse registrarQueimada(AlertaQueimadaRequest req) {
        Regiao regiao = regiaoRepository.findById(req.idRegiao())
            .orElseThrow(() -> new RuntimeException("Região não encontrada: " + req.idRegiao()));

        AlertaQueimada alerta = new AlertaQueimada();
        alerta.setTemperatura(req.temperatura());
        alerta.setNivelFumaca(req.nivelFumaca());
        alerta.setNivelRisco(calcularNivelQueimada(req.temperatura()));
        alertaRepository.save(alerta);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setRegiao(regiao);
        ocorrencia.setAlerta(alerta);
        ocorrencia.setStatus("ATIVO");
        ocorrenciaRepository.save(ocorrencia);

        return toResponse(ocorrencia);
    }

    @Transactional
    public OcorrenciaResponse registrarDesmatamento(AlertaDesmatamentoRequest req) {
        Regiao regiao = regiaoRepository.findById(req.idRegiao())
            .orElseThrow(() -> new RuntimeException("Região não encontrada: " + req.idRegiao()));

        AlertaDesmatamento alerta = new AlertaDesmatamento();
        alerta.setAreaHectares(req.areaHectares());
        alerta.setCoberturaVegetal(req.coberturaVegetal());
        alerta.setNivelRisco(calcularNivelDesmatamento(req.areaHectares()));
        alertaRepository.save(alerta);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setRegiao(regiao);
        ocorrencia.setAlerta(alerta);
        ocorrencia.setStatus("ATIVO");
        ocorrenciaRepository.save(ocorrencia);

        return toResponse(ocorrencia);
    }

    public Page<OcorrenciaResponse> listarOcorrencias(String status, Pageable pageable) {
        if (status != null) {
            return ocorrenciaRepository.findByStatus(status, pageable).map(this::toResponse);
        }
        return ocorrenciaRepository.findAll(pageable).map(this::toResponse);
    }

    public OcorrenciaResponse buscarOcorrencia(Long id) {
        return toResponse(ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada: " + id)));
    }

    @Transactional
    public OcorrenciaResponse resolverOcorrencia(Long id) {
        Ocorrencia oc = ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada: " + id));
        oc.setStatus("RESOLVIDO");
        oc.setDtResolucao(LocalDate.now());
        return toResponse(ocorrenciaRepository.save(oc));
    }

    @Transactional
    public void deletarOcorrencia(Long id) {
        if (!ocorrenciaRepository.existsById(id))
            throw new RuntimeException("Ocorrência não encontrada: " + id);
        ocorrenciaRepository.deleteById(id);
    }

    private String calcularNivelQueimada(Double temp) {
        if (temp >= 80) return "CRITICO";
        if (temp >= 60) return "MEDIO";
        return "BAIXO";
    }

    private String calcularNivelDesmatamento(Double area) {
        if (area >= 1000) return "CRITICO";
        if (area >= 300) return "MEDIO";
        return "BAIXO";
    }

    private OcorrenciaResponse toResponse(Ocorrencia o) {
        String tipo = o.getAlerta() instanceof AlertaQueimada ? "QUEIMADA" : "DESMATAMENTO";
        return new OcorrenciaResponse(
            o.getId(),
            o.getRegiao().getNome(),
            o.getRegiao().getEstado(),
            tipo,
            o.getAlerta().getNivelRisco(),
            o.getStatus(),
            o.getDtOcorrencia(),
            o.getDtResolucao()
        );
    }
}
