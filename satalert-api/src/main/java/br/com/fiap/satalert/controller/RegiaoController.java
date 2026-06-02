package br.com.fiap.satalert.controller;

import br.com.fiap.satalert.dto.*;
import br.com.fiap.satalert.service.RegiaoService;
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
@RequestMapping("/api/regioes")
@RequiredArgsConstructor
@Tag(name = "Regiões", description = "Gerenciamento de regiões monitoradas")
public class RegiaoController {

    private final RegiaoService regiaoService;

    @GetMapping
    @Operation(summary = "Lista regiões com paginação")
    public ResponseEntity<Page<RegiaoResponse>> listar(
        @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        return ResponseEntity.ok(regiaoService.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca região por ID com HATEOAS")
    public ResponseEntity<EntityModel<RegiaoResponse>> buscar(@PathVariable Long id) {
        RegiaoResponse resp = regiaoService.buscar(id);
        EntityModel<RegiaoResponse> model = EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoController.class).buscar(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RegiaoController.class).listar(Pageable.unpaged())).withRel("regioes")
        );
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Cria nova região monitorada")
    public ResponseEntity<RegiaoResponse> criar(@RequestBody @Valid RegiaoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regiaoService.criar(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza região")
    public ResponseEntity<RegiaoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid RegiaoRequest req) {
        return ResponseEntity.ok(regiaoService.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove região")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        regiaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
