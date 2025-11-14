package br.ifms.edu.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.ifms.edu.demo.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {


    @Query(value = "SELECT DISTINCT l FROM Livro l" +
            " left JOIN FETCH l.autores a" +
            " JOIN FETCH l.editora e",
            countQuery = "SELECT COUNT(DISTINCT l) FROM Livro l")
            Page<Livro> findAllDetalhes(Pageable pageable);

    @Query("SELECT l FROM Livro l JOIN FETCH l.editora LEFT JOIN FETCH l.autores WHERE l.id = :id")
    Optional<Livro> findByIdComDetalhes(Long id);

}
