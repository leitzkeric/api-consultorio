package medico.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import medico.api.domain.ValidacaoException;
import medico.api.domain.consulta.AgendaDeConsultas;
import medico.api.domain.consulta.DadosAgendamentoConsulta;
import medico.api.domain.consulta.DadosCancelamentoConsulta;
import medico.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) throws ValidacaoException {
        agenda.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) throws ValidacaoException {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
