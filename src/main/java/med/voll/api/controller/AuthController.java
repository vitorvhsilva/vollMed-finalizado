package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import med.voll.api.domain.usuario.DadosAuth;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@AllArgsConstructor
public class AuthController {

    // chama o auth service para fazer o login
    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAuth dadosAuth) {

        Authentication token = new UsernamePasswordAuthenticationToken(dadosAuth.login(), dadosAuth.senha());
        Authentication auth = authenticationManager.authenticate(token); // loadUserByUsername (valida se o usuario existe)

        return ResponseEntity.ok(new DadosTokenJWT(tokenService.gerarToken((Usuario) auth.getPrincipal())));
    }
}
