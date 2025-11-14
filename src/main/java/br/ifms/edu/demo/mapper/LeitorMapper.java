package br.ifms.edu.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ifms.edu.demo.dto.LeitorRegistroDTO;
import br.ifms.edu.demo.dto.LeitorResponseDTO;
import br.ifms.edu.demo.model.Leitor;

@Component
public class LeitorMapper {

    @Autowired
    private CartaoMapper cartaoMapper;

    public LeitorResponseDTO toResponseDTO(Leitor leitor){
        if (leitor == null) {
            return null;
        }
        return new LeitorResponseDTO(
            leitor.getId(),
            leitor.getNome(),
            leitor.getEmail(),
            cartaoMapper.toDTO(leitor.getCartao())
        );        
    }

    public Leitor toEntity(LeitorRegistroDTO dto){
        if (dto == null) {
            return null;
        }
        Leitor leitor = new Leitor();
        leitor.setNome(dto.nome());
        leitor.setEmail(dto.email());
        return leitor;
    }
    
}
