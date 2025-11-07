package br.ifms.edu.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifms.edu.demo.model.CartaoBiblioteca;

@Repository
public interface CartaoBibliotecaRepository extends JpaRepository<CartaoBiblioteca, Long> {
}
