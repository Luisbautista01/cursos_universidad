package com.Universidad.Cursos.Excepciones;

public class ProfesorNoEncontradoExepcion extends RuntimeException{
    public ProfesorNoEncontradoExepcion(Long profesorId) {
        super("Profesor con id " + profesorId + " no encontrado");
    }
}
