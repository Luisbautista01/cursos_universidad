package com.Universidad.Cursos.Excepciones;

import com.Universidad.Cursos.Modelo.Profesor;

public class TitulosAcademicosExepcion extends RuntimeException {
    public TitulosAcademicosExepcion(Profesor profesor){
        super("Ingrese los titulos academicos del profesor" + profesor);
    }
    public TitulosAcademicosExepcion(String mensaje){
        super(mensaje);
    }
    public TitulosAcademicosExepcion(String mensaje,Throwable erorMensaje){
        super(mensaje,erorMensaje);
    }
}
