package br.ifms.edu.demo.Controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifms.edu.demo.dto.AuthRequestDTO;
import br.ifms.edu.demo.dto.AuthResponseDTO;
import br.ifms.edu.demo.dto.LeitorRegistroDTO;
import br.ifms.edu.demo.dto.LeitorResponseDTO;
import br.ifms.edu.demo.model.Leitor;
import br.ifms.edu.demo.service.JwtService;
import br.ifms.edu.demo.service.LeitorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private LeitorService leitorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.senha());
         Authentication authentication = authenticationManager.authenticate(usernamePassword);

        Leitor leitor = (Leitor) authentication.getPrincipal();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nome", leitor.getNome());
        extraClaims.put("id", leitor.getId());
        extraClaims.put("role", leitor.getRoles());

        if (leitor.getCartao() != null) {
            extraClaims.put("cartao", leitor.getCartao().getNumero());
        }
        else {
            extraClaims.put("cartao", "Pendente");
        }

        String token = jwtService.generateToken(extraClaims, leitor);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<LeitorResponseDTO> registrar(@RequestBody @Valid LeitorRegistroDTO dto) {
        LeitorResponseDTO leitorRegistrado = leitorService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(leitorRegistrado);
    }
    
}
