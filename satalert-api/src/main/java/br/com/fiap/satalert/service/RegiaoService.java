package br.com.fiap.satalert.service;

import br.com.fiap.satalert.domain.embedded.Coordenada;
import br.com.fiap.satalert.domain.entity.Regiao;
import br.com.fiap.satalert.dto.*;
import br.com.fiap.satalert.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegiaoService {

    private final RegiaoRepository regiaoRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    public Page<RegiaoResponse> listar(Pageable pageable) {
        return regiaoRepository.findAll(pageable).map(this::toResponse);
    }

    public RegiaoResponse buscar(Long id) {
        return toResponse(regiaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Região não encontrada: " + id)));
    }

    @Transactional
    public RegiaoResponse criar(RegiaoRequest req) {
        Regiao r = new Regiao();
        r.setNome(req.nome());
        r.setEstado(req.estado());
        r.setBioma(req.bioma());
        r.setCoordenada(new Coordenada(req.latitude(), req.longitude()));
        return toResponse(regiaoRepository.save(r));
    }

    @Transactional
    public RegiaoResponse atualizar(Long id, RegiaoRequest req) {
        Regiao r = regiaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Região não encontrada: " + id));
        r.setNome(req.nome());
        r.setEstado(req.estado());
        r.setBioma(req.bioma());
        r.setCoordenada(new Coordenada(req.latitude(), req.longitude()));
        return toResponse(regiaoRepository.save(r));
    }

    @Transactional
    public void deletar(Long id) {
        if (!regiaoRepository.existsById(id))
            throw new RuntimeException("Região não encontrada: " + id);
        regiaoRepository.deleteById(id);
    }

    private String calcularRisco(Long id) {
        long criticos = ocorrenciaRepository.findByRegiaoId(id).stream()
            .filter(o -> "ATIVO".equals(o.getStatus()))
            .filter(o -> "CRITICO".equals(o.getAlerta().getNivelRisco()))
            .count();
        if (criticos >= 3) return "CRITICO";
        if (criticos >= 1) return "MEDIO";
        return "SEGURO";
    }

    private RegiaoResponse toResponse(Regiao r) {
        return new RegiaoResponse(
            r.getId(), r.getNome(), r.getEstado(), r.getBioma(),
            r.getCoordenada() != null ? r.getCoordenada().getLatitude() : null,
            r.getCoordenada() != null ? r.getCoordenada().getLongitude() : null,
            calcularRisco(r.getId())
        );
    }
}
