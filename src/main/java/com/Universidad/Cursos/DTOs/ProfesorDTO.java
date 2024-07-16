package com.Universidad.Cursos.DTOs;

import java.time.LocalDate;
import java.util.List;

//DTO para Profesor con Cantidad de Cursos
public class ProfesorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private List<String> titulosAcademicos;
    private LocalDate fechaInicioTrabajo;

    private int cantidadDeCursos;

    public ProfesorDTO(Long id, String nombre, String apellido, List<String> titulosAcademicos, LocalDate fechaInicioTrabajo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulosAcademicos = titulosAcademicos;
        this.fechaInicioTrabajo = fechaInicioTrabajo;
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

    public int getCantidadDeCursos() {
        return cantidadDeCursos;
    }

    public void setCantidadDeCursos(int cantidadDeCursos) {
        this.cantidadDeCursos = cantidadDeCursos;
    }
}
