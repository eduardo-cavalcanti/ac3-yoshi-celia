package br.com.bandtec.AC3Yoshi.agendamento;

import br.com.bandtec.AC3Yoshi.controle.MusicaController;
import br.com.bandtec.AC3Yoshi.dominio.Execucao;
import br.com.bandtec.AC3Yoshi.repositorio.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private MusicaRepository repository;

    @Autowired
    private MusicaController controller;


    @Scheduled(fixedRate = 180000)
    public void passarMusica(){

        if(!controller.filaExecucao.isEmpty()){
            Execucao execucao = controller.filaExecucao.poll();
            controller.listaExecutados.add(execucao);

            System.out.println("--------------------------------------------------");
            System.out.println("\nTocando agora: " + execucao.getMusica().getTitulo() +
                    ". Protocolo: " + execucao.getProtocolo());
            System.out.println("--------------------------------------------------");

        }   else {
            System.out.println("A Playlist está vazia");
        }
    }





}
