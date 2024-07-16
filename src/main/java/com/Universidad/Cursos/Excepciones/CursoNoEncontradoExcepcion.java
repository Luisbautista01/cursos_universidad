package com.Universidad.Cursos.Excepciones;

public class CursoNoEncontradoExcepcion extends RuntimeException {
    public CursoNoEncontradoExcepcion(Long cursoId) {
        super("Curso " + cursoId + " no encontrado");
    }
}
