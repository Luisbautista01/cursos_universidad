package com.Universidad.Cursos.Excepciones;

public class ProfesorConCursosAsociadosExepcion extends RuntimeException {
    public ProfesorConCursosAsociadosExepcion(Long profesorId) {
        super("No se pudo eliminar el profesor" + profesorId + ". Este tiene cursos asociados.");
    }
}
