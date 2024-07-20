package med.voll.api.domain.consulta;

import lombok.AllArgsConstructor;
import med.voll.api.domain.consulta.validacoes.ValidadorConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsultaService {

    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;
    private List<ValidadorConsulta> validadores;

    public ResponseEntity agendar(DadosAgendamentoConsulta dados) {

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("paciente nao existe!");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("medico nao existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        Medico medico = escolherMedico(dados);

        if (medico == null) throw new ValidacaoException("nao existe medico disponivel nessa data");

        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
        Consulta consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.findById(dados.idMedico()).get();
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("especialidade obrigatoria!");
        }

        return medicoRepository.escolherMedicoAleaLivreNaData(dados.especialidade(), dados.data());

    }

}
