package com.Universidad.Cursos.Excepciones;

import com.Universidad.Cursos.Modelo.Estudiante;

public class MenorDeEdadExcepcion extends RuntimeException {
    public MenorDeEdadExcepcion(Estudiante estudiante) {
        super("El estudiante " + estudiante + " es menor de edad.");
    }

    public MenorDeEdadExcepcion(String mensaje) {
        super(mensaje);
    }

    public MenorDeEdadExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
