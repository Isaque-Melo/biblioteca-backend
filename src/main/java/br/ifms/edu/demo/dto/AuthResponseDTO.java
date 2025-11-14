package br.ifms.edu.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthResponseDTO(
    
        @NotBlank
        String token
    
) { }
