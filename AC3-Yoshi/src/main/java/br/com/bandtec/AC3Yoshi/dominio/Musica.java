package br.com.bandtec.AC3Yoshi.dominio;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Musica {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min=3)
    private String titulo;

    @NotBlank
    private String autor;

    @ManyToOne
    private GeneroMusica genero;


    // Get's e Set's

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public GeneroMusica getGenero() {
        return genero;
    }

    public void setGenero(GeneroMusica genero) {
        this.genero = genero;
    }

}
