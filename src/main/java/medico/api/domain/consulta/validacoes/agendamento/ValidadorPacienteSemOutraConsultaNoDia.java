package medico.api.domain.consulta.validacoes.agendamento;

import medico.api.domain.ValidacaoException;
import medico.api.domain.consulta.ConsultaRepository;
import medico.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar (DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoMesmoDia = repository.existsByPacienteIdAndDataBetweenAndAtivoIsTrue(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if (pacientePossuiOutraConsultaNoMesmoDia) {
            throw new ValidacaoException("Paciente já possui outra consulta agendada neste dia.");
        }
    }
}

