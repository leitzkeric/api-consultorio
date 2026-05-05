package medico.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import medico.api.domain.paciente.DadosCadastroPaciente;
import medico.api.domain.paciente.DadosListaPaciente;
import medico.api.domain.paciente.Paciente;
import medico.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){
        repository.save(new Paciente(dados));
    }
    @GetMapping
    public Page<DadosListaPaciente> listar(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListaPaciente::new);
    }
}
