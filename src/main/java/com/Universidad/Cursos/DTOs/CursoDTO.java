package com.Universidad.Cursos.DTOs;

//DTO para Curso con Nombre de Profesor
public class CursoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String materia;

    private String nombreProfesor;

    public CursoDTO(Long id, String nombre, String descripcion, String materia, String nombreProfesor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.materia = materia;
        this.nombreProfesor = nombreProfesor;
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

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }
}
