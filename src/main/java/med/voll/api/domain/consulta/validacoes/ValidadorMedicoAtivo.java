package med.voll.api.domain.consulta.validacoes;

import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidadorMedicoAtivo implements ValidadorConsulta{

    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null){
            return;
        }

        boolean medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if (!medicoAtivo) {
            throw new ValidacaoException("Medico inativo!");
        }

    }
}
