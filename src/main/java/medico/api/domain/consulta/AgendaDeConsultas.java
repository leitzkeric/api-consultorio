package medico.api.domain.consulta;

import medico.api.domain.ValidacaoException;
import medico.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import medico.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import medico.api.domain.medico.Medico;
import medico.api.domain.medico.MedicoRepository;
import medico.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados)  {
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Não foi encontrado paciente para este id");
        }
        validadores.forEach(v ->  v.validar(dados));
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível na data/hora.");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data(),null, true);
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados)  {
        if (dados.idMedico() != null && medicoRepository.existsById(dados.idMedico())){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw  new ValidacaoException("Especialidade é obrigatória quando o médico não for informado.");
        }
        return medicoRepository.escolherMedicoAleatorio(dados.especialidade(), dados.data());


    }

    public void cancelar(DadosCancelamentoConsulta dados)  {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
