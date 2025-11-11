package br.ifms.edu.demo.mapper;

import org.springframework.stereotype.Component;
import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.dto.EditoraDTO;

@Component
public class EditoraMapper {

    public EditoraDTO toDTO(Editora editora) {
        if (editora == null) {
            return null;
        }
        return new EditoraDTO(editora.getId(), editora.getNome(), editora.getCidade());
    }

    public Editora toEntity(EditoraDTO dto) {
        if (dto == null) {
            return null;
        }
        Editora editora = new Editora();
        editora.setId(dto.id());
        editora.setNome(dto.nome());
        editora.setCidade(dto.cidade());
        return editora;
    }
}