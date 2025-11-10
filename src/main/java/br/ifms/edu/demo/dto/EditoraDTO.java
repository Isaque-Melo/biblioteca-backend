package br.ifms.edu.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record EditoraDTO(
    Long id,
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotBlank(message = "Cidade é obrigatória")
    String cidade
) {}