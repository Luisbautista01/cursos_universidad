package com.Universidad.Cursos.Excepciones;

public class EstudianteNoEncontradoExcepcion extends RuntimeException{
    public EstudianteNoEncontradoExcepcion(Long id) {
        super("Estudiante con id " + id + " no encontrado.");
    }
}
