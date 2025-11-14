package br.ifms.edu.demo.dto;
import java.time.LocalDate;

public record CartaoDTO(
    String numero,
    LocalDate dataEmissao
) {
}
