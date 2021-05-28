package br.com.bandtec.AC3Yoshi.controle;

import br.com.bandtec.AC3Yoshi.dominio.GeneroMusica;
import br.com.bandtec.AC3Yoshi.dominio.Musica;
import br.com.bandtec.AC3Yoshi.repositorio.GeneroMusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/generos")
public class GeneroMusicaController {

    @Autowired
    private GeneroMusicaRepository repository;

    @GetMapping
    public ResponseEntity getGeneroMusica() {
        List<GeneroMusica> listaGeneroMusicas = repository.findAll();

        if (listaGeneroMusicas.isEmpty()) {
            return ResponseEntity.status(204).body("Não há gêneros na lista");
        } else {
            return ResponseEntity.status(200).body(listaGeneroMusicas);
        }
    }

    @PostMapping("criar")
    public ResponseEntity postGeneroMusica(@RequestBody @Valid GeneroMusica novoGeneroMusica) {
        repository.save(novoGeneroMusica);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("deletar/{idGeneroMusica}")
    public ResponseEntity deleteGeneroMusica(@PathVariable Integer idGeneroMusica) {
        repository.deleteById(idGeneroMusica);
        return ResponseEntity.status(200).body("Gênero deletado");
    }

    @PutMapping("editar/{idGeneroMusica}")
    public ResponseEntity putGeneroMusica(@PathVariable Integer idGeneroMusica,
                                          @RequestBody @Valid GeneroMusica novoGeneroMusica) {

        if(repository.existsById(idGeneroMusica)){
            novoGeneroMusica.setId(idGeneroMusica);
            repository.save(novoGeneroMusica);
            return ResponseEntity.status(201).build();

        } else {
            return ResponseEntity.status(204).body("Gênero não encontrado");
        }


    }
}
