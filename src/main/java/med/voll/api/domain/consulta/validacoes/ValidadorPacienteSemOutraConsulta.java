package med.voll.api.domain.consulta.validacoes;

import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class ValidadorPacienteSemOutraConsulta implements ValidadorConsulta{

    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime primeiroH = dados.data().withHour(7);
        LocalDateTime ultimoH = dados.data().withHour(18);
        Boolean pacientePossuiConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween
                (dados.idPaciente(), primeiroH, ultimoH);

        if (pacientePossuiConsultaNoDia) throw new ValidacaoException("paciente ja possui consulta no dia");

    }
}
