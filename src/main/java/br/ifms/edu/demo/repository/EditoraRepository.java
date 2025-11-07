package br.ifms.edu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifms.edu.demo.model.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
}
