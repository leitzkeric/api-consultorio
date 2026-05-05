package medico.api.domain.consulta.validacoes;

import medico.api.domain.ValidacaoException;
import medico.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
