package br.com.bandtec.AC3Yoshi.controle;

import br.com.bandtec.AC3Yoshi.dominio.Musica;
import br.com.bandtec.AC3Yoshi.repositorio.GeneroMusicaRepository;
import br.com.bandtec.AC3Yoshi.repositorio.MusicaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicaControllerTest {

    @Autowired
    MusicaController controller;

    @MockBean
    MusicaRepository repository;

    @MockBean
    GeneroMusicaRepository generoMusicaRepository;

    @Test
    @DisplayName("GET /musicas - Se tiver registros: Status 200 e quantidade de registros")
    void getMusicasComRegistros() {

        List<Musica> musicasTeste = Arrays.asList(new Musica());
        Mockito.when(repository.findAll()).thenReturn(musicasTeste);

        ResponseEntity<List<Musica>> resposta = controller.getMusicas();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(1, resposta.getBody().size());

    }

    @Test
    void postMusica() {
    }

    @Test
    void deleteMusica() {
    }

    @Test
    void putMusica() {
    }
}