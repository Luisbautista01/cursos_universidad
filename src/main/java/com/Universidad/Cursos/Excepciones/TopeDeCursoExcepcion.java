package com.Universidad.Cursos.Excepciones;

public class TopeDeCursoExcepcion extends RuntimeException {
    public TopeDeCursoExcepcion(Long cursoId, Long estudianteId) {
        super("No se puedo matricular el estudiante con id " + estudianteId + " .¡El curso " + cursoId + " ya ha alcanzado el número máximo de estudiantes!.");
    }

    public TopeDeCursoExcepcion(String mensaje) {
        super(mensaje);
    }

    public TopeDeCursoExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
