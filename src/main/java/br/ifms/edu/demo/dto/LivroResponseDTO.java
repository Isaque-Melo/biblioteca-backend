package br.ifms.edu.demo.dto;

import java.util.List;

public record LivroResponseDTO(
    Long id,
    String titulo,
    int anoPublicacao,
    String nomeEditora,    
    List<String> nomesAutores 
) {}