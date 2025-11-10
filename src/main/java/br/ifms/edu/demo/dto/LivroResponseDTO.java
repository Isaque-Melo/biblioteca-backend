package br.ifms.edu.demo.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record LivroResponseDTO(
    @Schema(description = "ID do livro", example = "1")
    Long id,
    @Schema(description = "Título do livro", example = "Dom Casmurro")
    String titulo,
    @Schema(description = "Ano de publicação do livro", example = "1899")
    int anoPublicacao,
    @Schema(description = "Nome da editora", example = "Editora X")
    String nomeEditora,
    @Schema(description = "Lista de nomes dos autores", example = "[\"Autor 1\", \"Autor 2\"]")
    List<String> nomesAutores
) {}