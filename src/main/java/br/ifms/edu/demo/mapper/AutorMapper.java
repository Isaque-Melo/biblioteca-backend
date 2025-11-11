package br.ifms.edu.demo.mapper;

import org.springframework.stereotype.Component;
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.dto.AutorDTO;

@Component // Permite que o Spring o injete em outras classes
public class AutorMapper {

    // Lógica movida do seu controller
    public AutorDTO toDTO(Autor autor) {
        if (autor == null) {
            return null;
        }
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade());
    }

    // Lógica movida do seu controller
    public Autor toEntity(AutorDTO dto) {
        if (dto == null) {
            return null;
        }
        Autor autor = new Autor();
        // O 'id' do DTO será usado para atualizações. Em criações, ele é nulo.
        autor.setId(dto.id()); 
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        return autor;
    }
}