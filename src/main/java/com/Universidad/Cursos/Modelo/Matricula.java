package com.Universidad.Cursos.Modelo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaMatricula;
    private LocalDate fechaDesmatricula;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante; //Muchas matriculas pertenecen a un único Estudiante.

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso; //Muchas Matriculas pertenecen a un único Curso.

    public Matricula() {
    }

    public Matricula(Long id, LocalDate fechaMatricula, LocalDate fechaDesmatricula, Estudiante estudiante, Curso curso) {
        this.id = id;
        this.fechaMatricula = fechaMatricula;
        this.fechaDesmatricula = fechaDesmatricula;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    //Constructor utilizado al momento de crear una nueva matricula
    public Matricula(Curso curso, Estudiante estudiante, LocalDate fechaMatricula) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.fechaMatricula = fechaMatricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public LocalDate getFechaDesmatricula() {
        return fechaDesmatricula;
    }

    public void setFechaDesmatricula(LocalDate fechaDesmatricula) {
        this.fechaDesmatricula = fechaDesmatricula;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}