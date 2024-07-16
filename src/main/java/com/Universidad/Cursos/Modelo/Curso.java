package com.Universidad.Cursos.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String materia;
    private int maximoEstudiantes; //Agregamos un límite máximo de estudiantes
    private String semestre; //un curso pertenece a un semestre específico

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor; //Muchos cursos son dictados por un único Profesor.

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @JsonIgnore//Esta anotación se puede usar a nivel de setters, getters o de método. Evita que se serialice la relación inversa
    private Set<Matricula> matricula = new HashSet<>(); // Un curso puede tener muchos Estudiante a través de la matricula

    //Extra:
    // Lista de estudiantes matriculados en el curso atraves de la matricula
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "curso_estudiantes",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "estudiante_id"))
    private List<Estudiante> estudiantes;

    public Curso() {
    }

    //Los cursos se registran con un nombre, una descripción, la materia a la que pertenecen
    // y el profesor que dicta el curso. Un curso también tiene una lista de estudiantes matriculados.
    public Curso(Long id, String nombre, String materia, String descripcion, int maximoEstudiantes, Profesor profesor, Set<Matricula> matricula, String semestre, List<Estudiante> estudiantes) {
        this.id = id;
        this.nombre = nombre;
        this.materia = materia;
        this.descripcion = descripcion;
        this.maximoEstudiantes = maximoEstudiantes;
        this.profesor = profesor;
        this.matricula = matricula;
        this.semestre = semestre;
        this.estudiantes = estudiantes;
    }

    public boolean cursoLlegoAlTope() {
        return matricula.size() >= maximoEstudiantes;
    }

    public void agregarMatricula(Matricula matricula) {
        this.matricula.add(matricula);
        matricula.setCurso(this);
    }

    public void eliminarMatricula(Matricula matricula) {
        this.matricula.remove(matricula);
        matricula.setCurso(null);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public int getMaximoEstudiantes() {
        return maximoEstudiantes;
    }

    public void setMaximoEstudiantes(int maximoEstudiantes) {
        this.maximoEstudiantes = maximoEstudiantes;
    }

    public Set<Matricula> getMatricula() {
        return matricula;
    }

    public void setMatricula(Set<Matricula> matricula) {
        this.matricula = matricula;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}