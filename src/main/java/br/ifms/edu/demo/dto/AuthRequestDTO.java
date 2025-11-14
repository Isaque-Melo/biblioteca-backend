package br.ifms.edu.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
    
}
