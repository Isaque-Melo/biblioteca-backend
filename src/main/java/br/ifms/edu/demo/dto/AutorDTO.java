package br.ifms.edu.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutorDTO(
    @Schema(description = "ID do autor", example = "1")
    Long id,

    @NotBlank(message = "O nome do autor não pode ser vazio")
    @Size(min = 2, max = 100, message = "O nome do autor deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome do autor", example = "Machado de Assis", required = true)
    String nome,

    @Schema(description = "Nacionalidade do autor", example = "Brasileira", required = true)
    @NotBlank(message = "A nacionalidade do autor não pode ser vazia")
    String nacionalidade
) {}