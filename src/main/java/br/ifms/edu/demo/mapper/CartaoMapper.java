package br.ifms.edu.demo.mapper;

import org.springframework.stereotype.Component;

import br.ifms.edu.demo.dto.CartaoDTO;
import br.ifms.edu.demo.model.CartaoBiblioteca;

@Component
public class CartaoMapper {
    public CartaoDTO toDTO(CartaoBiblioteca cartao) {
        if (cartao == null) {
            return null;
        }
        return new CartaoDTO(cartao.getNumero(), cartao.getDataEmissao());
       
    }
}
