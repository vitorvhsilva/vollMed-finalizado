package med.voll.api.domain.consulta.validacoes;

import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidadorMedicoConsultaNoMesmoHorario implements ValidadorConsulta{

    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        Boolean medicoPossui = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossui) {
            throw new ValidacaoException("medico ja possui outra consulta agendada pro horario");
        }
    }

}
