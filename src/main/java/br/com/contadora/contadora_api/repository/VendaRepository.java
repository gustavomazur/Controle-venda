package br.com.contadora.contadora_api.repository;
import br.com.contadora.contadora_api.dto.VendaDTO;
import br.com.contadora.contadora_api.model.venda.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

        List<Venda> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

        List<Venda> findByClienteId(Long clienteId);
    }

