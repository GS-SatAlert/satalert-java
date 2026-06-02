package br.com.fiap.satalert.service;

import br.com.fiap.satalert.domain.entity.Usuario;
import br.com.fiap.satalert.dto.RegistroRequest;
import br.com.fiap.satalert.dto.UsuarioResponse;
import br.com.fiap.satalert.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest req) {
        if (usuarioRepository.existsByEmail(req.email())) {
            throw new RuntimeException("E-mail já cadastrado: " + req.email());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(req.nome());
        usuario.setEmail(req.email());
        usuario.setSenha(passwordEncoder.encode(req.senha()));
        usuario.setTelefone(req.telefone());
        usuario.setRole("ROLE_USER");

        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse buscar(Long id) {
        return toResponse(usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id)));
    }

    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, RegistroRequest req) {
        Usuario u = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
        u.setNome(req.nome());
        u.setTelefone(req.telefone());
        if (req.senha() != null && !req.senha().isBlank()) {
            u.setSenha(passwordEncoder.encode(req.senha()));
        }
        return toResponse(usuarioRepository.save(u));
    }

    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuário não encontrado: " + id);
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail(), u.getTelefone(), u.getRole(), u.getDtCadastro());
    }
}
