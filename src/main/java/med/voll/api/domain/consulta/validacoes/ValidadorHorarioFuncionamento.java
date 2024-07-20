package med.voll.api.domain.consulta.validacoes;

import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime dataConsulta = dados.data();
        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean aberturaClinica = dataConsulta.getHour() < 7;
        boolean fechamentoClinica = dataConsulta.getHour() > 18;

        if (domingo || aberturaClinica || fechamentoClinica) {
            throw new ValidacaoException("consulta fora do horario de funcionamento");
        }

    }

}
