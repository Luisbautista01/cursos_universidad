package com.Universidad.Cursos.Excepciones;

public class EstudianteMatriculadoExcepcion extends RuntimeException {
    public EstudianteMatriculadoExcepcion(Long cursoId, Long estudianteId) {
        super("El estudiante" + estudianteId + " ya está matriculado en este curso " + cursoId);
    }
}
