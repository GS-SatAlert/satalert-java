package br.com.fiap.satalert.controller;

import br.com.fiap.satalert.dto.*;
import br.com.fiap.satalert.security.JwtService;
import br.com.fiap.satalert.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Registro, login e geração de token JWT")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    @Operation(summary = "Cria novo usuário")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid RegistroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(req));
    }

    @PostMapping("/login")
    @Operation(summary = "Autentica usuário e retorna JWT")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.senha()));
        UserDetails user = usuarioService.loadUserByUsername(req.email());
        String token = jwtService.gerarToken(user);
        return ResponseEntity.ok(new TokenResponse(token, "Bearer", 86400000L));
    }
}
