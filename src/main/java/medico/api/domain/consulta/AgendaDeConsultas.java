package medico.api.domain.consulta;

import medico.api.domain.ValidacaoException;
import medico.api.domain.medico.Medico;
import medico.api.domain.medico.MedicoRepository;
import medico.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados) throws ValidacaoException {
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Não foi encontrado paciente para este id");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        var medico = escolherMedico(dados);

        var consulta = new Consulta(null, medico, paciente, dados.data(),null);
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) throws ValidacaoException {
        if (dados.idMedico() != null && medicoRepository.existsById(dados.idMedico())){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw  new ValidacaoException("Especialidade é obrigatória quando o médico não for informado.");
        }
        return medicoRepository.escolherMedicoAleatorio(dados.especialidade(), dados.data());


    }

    public void cancelar(DadosCancelamentoConsulta dados) throws ValidacaoException {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
