package com.Universidad.Cursos.Excepciones;

public class CursosYSemestresNoEncontradosExcepcion extends RuntimeException {
    public CursosYSemestresNoEncontradosExcepcion(Long id, String semestre) {
        super("No se encontraron cursos para el estudiante con id " + id + " en el semestre " + semestre + ".");
    }
}
