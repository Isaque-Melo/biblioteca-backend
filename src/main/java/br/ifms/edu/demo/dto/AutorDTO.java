package br.ifms.edu.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutorDTO(
    Long id,
    @NotBlank(message = "O nome do autor não pode ser vazio")
    @Size(min = 2, max = 100, message = "O nome do autor deve ter entre 2 e 100 caracteres")
    String nome,
    @NotBlank(message = "A nacionalidade do autor não pode ser vazia")
    String nacionalidade
) {}