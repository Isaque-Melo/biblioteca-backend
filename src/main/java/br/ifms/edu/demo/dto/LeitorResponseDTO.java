package br.ifms.edu.demo.dto;

public record LeitorResponseDTO (
    Long id,
    String nome,
    String email,
    CartaoDTO cartao
) {
}
