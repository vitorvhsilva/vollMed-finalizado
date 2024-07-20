package med.voll.api.domain.consulta.validacoes;

import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime dataConsulta = dados.data();
        LocalDateTime agora = LocalDateTime.now();
        Long diferenca = Duration.between(agora, dataConsulta).toMinutes();

        if (diferenca < 30) {
            throw new ValidacaoException("consulta deve ser agendada com antecedencia");
        }

    }
}
