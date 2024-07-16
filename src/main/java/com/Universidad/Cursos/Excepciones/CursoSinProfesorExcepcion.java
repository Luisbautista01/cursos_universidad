package com.Universidad.Cursos.Excepciones;

public class CursoSinProfesorExcepcion extends RuntimeException {
    public CursoSinProfesorExcepcion() {
        super("El curso no tiene pofesores registrados");
    }
}
