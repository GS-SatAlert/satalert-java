package br.com.fiap.satalert.controller;

import br.com.fiap.satalert.dto.*;
import br.com.fiap.satalert.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID com HATEOAS")
    public ResponseEntity<EntityModel<UsuarioResponse>> buscar(@PathVariable Long id) {
        UsuarioResponse resp = usuarioService.buscar(id);
        EntityModel<UsuarioResponse> model = EntityModel.of(resp,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios")
        );
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza usuário")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody @Valid RegistroRequest req) {
        return ResponseEntity.ok(usuarioService.atualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
