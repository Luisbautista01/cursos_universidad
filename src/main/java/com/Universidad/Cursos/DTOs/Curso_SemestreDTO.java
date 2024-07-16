package com.Universidad.Cursos.DTOs;

import java.util.List;

//DTO para Cursos de un Estudiante en un Semestre específico
public class Curso_SemestreDTO {
    private String semestre;
    private List<CursoDTO> cursoDTO;

    public Curso_SemestreDTO(String semestre, List<CursoDTO> cursoDTO) {
        this.semestre = semestre;
        this.cursoDTO = cursoDTO;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<CursoDTO> getCursoDTO() {
        return cursoDTO;
    }

    public void setCursoDTO(List<CursoDTO> cursoDTO) {
        this.cursoDTO = cursoDTO;
    }
}
