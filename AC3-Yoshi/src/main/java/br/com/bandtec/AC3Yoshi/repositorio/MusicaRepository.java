package br.com.bandtec.AC3Yoshi.repositorio;

import br.com.bandtec.AC3Yoshi.dominio.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Integer> {
}
