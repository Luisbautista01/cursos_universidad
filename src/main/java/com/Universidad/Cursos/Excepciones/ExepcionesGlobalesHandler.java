package com.Universidad.Cursos.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExepcionesGlobalesHandler {

    @ExceptionHandler(CursoNoEncontradoExcepcion.class)
    public ResponseEntity<String> handleCursoNoencontradoExcepcion(CursoNoEncontradoExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CursoSinProfesorExcepcion.class)
    public ResponseEntity<String> handleCursoSinProfesorExcepcion(CursoSinProfesorExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CursosYSemestresNoEncontradosExcepcion.class)
    public ResponseEntity<String> handleCursosYSemestresNoEncontradosExcepcion(CursosYSemestresNoEncontradosExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstudianteMatriculadoExcepcion.class)
    public ResponseEntity<String> handleEstudianteMatriculadoExcepcion(EstudianteMatriculadoExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EstudianteNoEncontradoExcepcion.class)
    public ResponseEntity<String> handleEstudianteNoEncontadoExcepcion(EstudianteNoEncontradoExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfesorConCursosAsociadosExepcion.class)
    public ResponseEntity<String> handleProfesorConCursosAsociadosExepcion(ProfesorConCursosAsociadosExepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MenorDeEdadExcepcion.class)
    public ResponseEntity<String> handleMenorDeEdadExcepcion(MenorDeEdadExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfesorNoEncontradoExepcion.class)
    public ResponseEntity<String> handleProfesorNoEncontadoExcepcion(ProfesorNoEncontradoExepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TitulosAcademicosExepcion.class)
    public ResponseEntity<String> handleTitulosAcademicosExcepcion(TitulosAcademicosExepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TopeDeCursoExcepcion.class)
    public ResponseEntity<String> handleTopeDeCursoExcepcion(TopeDeCursoExcepcion e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e, WebRequest request) {
        return new ResponseEntity<>("Ocurrió un error inesperado.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
