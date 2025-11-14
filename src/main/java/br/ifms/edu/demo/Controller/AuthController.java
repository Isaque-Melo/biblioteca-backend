package br.ifms.edu.demo.Controller;

import java.net.Authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifms.edu.demo.dto.AuthRequestDTO;
import br.ifms.edu.demo.dto.AuthResponseDTO;
import br.ifms.edu.demo.model.Leitor;
import br.ifms.edu.demo.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.senha());
         Authentication authentication = authenticationManager.authenticate(usernamePassword);

        Leitor leitor = (Leitor) authentication.getPrincipal();
        String token =jwtService.generateToken(leitor);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
    
}
