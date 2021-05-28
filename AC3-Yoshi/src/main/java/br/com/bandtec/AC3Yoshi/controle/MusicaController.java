package br.com.bandtec.AC3Yoshi.controle;

import br.com.bandtec.AC3Yoshi.dominio.Execucao;
import br.com.bandtec.AC3Yoshi.dominio.FilaObj;
import br.com.bandtec.AC3Yoshi.dominio.Musica;
import br.com.bandtec.AC3Yoshi.dominio.PilhaObj;
import br.com.bandtec.AC3Yoshi.repositorio.GeneroMusicaRepository;
import br.com.bandtec.AC3Yoshi.repositorio.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.mscapi.CPublicKey;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaRepository repository;

    @Autowired
    private GeneroMusicaRepository generoMusicaRepository;

    @GetMapping
    public ResponseEntity getMusicas() {
        List<Musica> listaMusicas = repository.findAll();

        if (listaMusicas.isEmpty()) {
            return ResponseEntity.status(204).body("Não há músicas na lista");
        } else {
            return ResponseEntity.status(200).body(listaMusicas);
        }
    }

    @PostMapping("criar")
    public ResponseEntity postMusica(@RequestBody @Valid Musica novaMusica) {
        repository.save(novaMusica);
        return ResponseEntity.status(201).build();
    }


    PilhaObj<Musica> pilhaMusicas = new PilhaObj<Musica>(8);

    @DeleteMapping("deletar/{idMusica}")
    public ResponseEntity deleteMusica(@PathVariable Integer idMusica) {
        if (repository.existsById(idMusica)) {

            Musica musica = new Musica();
            musica.setId(idMusica);
            musica.setTitulo(repository.findById(idMusica).get().getTitulo());
            musica.setAutor(repository.findById(idMusica).get().getAutor());
            musica.setGenero(repository.findById(idMusica).get().getGenero());

            pilhaMusicas.push(musica);

            repository.deleteById(idMusica);
            return ResponseEntity.status(200).body("Música deletada");

        } else {
            return ResponseEntity.status(204).body("Música não encontrada");
        }
    }

    @PutMapping("editar/{idMusica}")
    public ResponseEntity putMusica(@PathVariable Integer idMusica, @RequestBody @Valid Musica novaMusica) {
        if (repository.existsById(idMusica)) {
            novaMusica.setId(idMusica);
            repository.save(novaMusica);
            return ResponseEntity.status(201).build();

        } else {
            return ResponseEntity.status(204).body("Música não encontrada");
        }
    }

    @GetMapping("desfazer-delete")
    public ResponseEntity getDesfazerDeleteMusica() {
        if (!pilhaMusicas.isEmpty()) {
            repository.save(pilhaMusicas.pop());
            return ResponseEntity.status(201).body("Música readicionada");

        } else {
            return ResponseEntity.status(204).body("Não há músicas para readicionar");
        }
    }

    public FilaObj<Execucao> filaExecucao = new FilaObj<Execucao>(15);
    public List<Execucao> listaExecutados = new ArrayList();

    @PostMapping("criar-playlist/{idMusica}")
    public ResponseEntity postAdicionarPlaylist(@PathVariable Integer idMusica) {
        if (repository.existsById(idMusica)) {

            Musica musica = new Musica();
            musica.setId(idMusica);
            musica.setTitulo(repository.findById(idMusica).get().getTitulo());
            musica.setAutor(repository.findById(idMusica).get().getAutor());
            musica.setGenero(repository.findById(idMusica).get().getGenero());

            Execucao execucao = new Execucao();
            execucao.setProtocolo(UUID.randomUUID().toString());
            execucao.setMusica(musica);

            filaExecucao.insert(execucao);

            return ResponseEntity.status(201).body("Música adicionada à sua Playlist." +
                    " Protocolo: " + execucao.getProtocolo());

        } else {
            return ResponseEntity.status(204).body("Música não encontrada");
        }
    }

    @GetMapping("ouvir-playlist")
    public ResponseEntity getOuvirPlaylist() {
        if (!filaExecucao.isEmpty()) {
            Execucao execucao = filaExecucao.poll();
            listaExecutados.add(execucao);

            return ResponseEntity.status(200).body("Tocando agora: " + execucao.getMusica().getTitulo() +
                    " Protocolo: " + execucao.getProtocolo());
        } else {
            return ResponseEntity.status(204).body("Sua Playlist está vazia!");
        }
    }

    @GetMapping("playlist/{protocolo}")
    public ResponseEntity getPlaylist(@PathVariable String protocolo) {
        if (!listaExecutados.isEmpty()) {
            for (int i = 0; i < listaExecutados.size(); i++) {
                if (listaExecutados.get(i).getProtocolo().equals(protocolo)) {
                    return ResponseEntity.status(200).body("Já executada");
                }
            }
        }
        return ResponseEntity.status(204).body("Não executado ou inexistente");
    }
}

