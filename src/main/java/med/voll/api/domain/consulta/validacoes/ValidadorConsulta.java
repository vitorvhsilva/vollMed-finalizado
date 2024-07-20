package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorConsulta {
    void validar(DadosAgendamentoConsulta dados);
}
