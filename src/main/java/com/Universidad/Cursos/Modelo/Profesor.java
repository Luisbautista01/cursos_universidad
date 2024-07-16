package com.Universidad.Cursos.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;

    @ElementCollection
    @CollectionTable(
            name = "profesor_titulos_academicos",
            joinColumns = @JoinColumn(name = "profesor_id")
    )
    @Column(name = "titulo_academico")
    private List<String> titulosAcademicos;

    private LocalDate fechaInicioTrabajo;

    //Lista de cursos que el profesor dicta
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    @JsonIgnore // Evita que se serialice la relación inversa
    private List<Curso> cursos = new ArrayList<>(); // Un Profesor puede dictar muchos Cursos.

    public Profesor() {
    }

    // Los profesores se registran con un nombre, apellido, listado de titulos académicos y
    // la fecha en la que comenzaron a trabajar en la institución
    public Profesor(Long id, String nombre, String apellido, List<String> titulosAcademicos, LocalDate fechaInicioTrabajo, List<Curso> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulosAcademicos = titulosAcademicos;
        this.fechaInicioTrabajo = fechaInicioTrabajo;
        this.cursos = cursos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<String> getTitulosAcademicos() {
        return titulosAcademicos;
    }

    public void setTitulosAcademicos(List<String> titulosAcademicos) {
        this.titulosAcademicos = titulosAcademicos;
    }

    public LocalDate getFechaInicioTrabajo() {
        return fechaInicioTrabajo;
    }

    public void setFechaInicioTrabajo(LocalDate fechaInicioTrabajo) {
        this.fechaInicioTrabajo = fechaInicioTrabajo;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
