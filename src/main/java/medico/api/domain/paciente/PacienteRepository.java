package medico.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean findAtivoById(@NotNull Long idPaciente);
}
