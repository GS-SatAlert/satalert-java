package br.com.fiap.satalert.controller;

import br.com.fiap.satalert.dto.*;
import br.com.fiap.satalert.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocorrencias")
@RequiredArgsConstructor
@Tag(name = "Ocorrências", description = "Gerenciamento de ocorrências de queimadas e desmatamento")
public class OcorrenciaController {

    private final AlertaService alertaService;

    @PostMapping("/queimada")
    @Operation(summary = "Registra nova ocorrência de queimada (chamado pelo ESP32)")
    public ResponseEntity<OcorrenciaResponse> registrarQueimada(@RequestBody @Valid AlertaQueimadaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.registrarQueimada(req));
    }

    @PostMapping("/desmatamento")
    @Operation(summary = "Registra nova ocorrência de desmatamento")
    public ResponseEntity<OcorrenciaResponse> registrarDesmatamento(@RequestBody @Valid AlertaDesmatamentoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.registrarDesmatamento(req));
    }

    @GetMapping
    @Operation(summary = "Lista ocorrências com paginação")
    public ResponseEntity<Page<OcorrenciaResponse>> listar(
        @RequestParam(required = false) String status,
        @PageableDefault(size = 10, sort = "dtOcorrencia", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(alertaService.listarOcorrencias(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca ocorrência por ID com HATEOAS")
    public ResponseEntity<EntityModel<OcorrenciaResponse>> buscar(@PathVariable Long id) {
        OcorrenciaResponse resp = alertaService.buscarOcorrencia(id);
        EntityModel<OcorrenciaResponse> model = EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class).buscar(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class).listar(null, Pageable.unpaged())).withRel("ocorrencias"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OcorrenciaController.class).resolver(id)).withRel("resolver")
        );
        return ResponseEntity.ok(model);
    }

    @PatchMapping("/{id}/resolver")
    @Operation(summary = "Marca ocorrência como resolvida")
    public ResponseEntity<OcorrenciaResponse> resolver(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.resolverOcorrencia(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove ocorrência")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaService.deletarOcorrencia(id);
        return ResponseEntity.noContent().build();
    }
}
