package com.Universidad.Cursos.Controlador;

import com.Universidad.Cursos.Excepciones.CursoNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.EstudianteMatriculadoExcepcion;
import com.Universidad.Cursos.Excepciones.EstudianteNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.TopeDeCursoExcepcion;
import com.Universidad.Cursos.Modelo.Matricula;
import com.Universidad.Cursos.Servicio.MatriculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Matriculas")
public class MatriculaControlador {

    private final MatriculaServicio matriculaServicio;

    @Autowired
    public MatriculaControlador(MatriculaServicio matriculaServicio) {
        this.matriculaServicio = matriculaServicio;
    }

    @PostMapping("/curso/{cursoId}/matricular/estudiante/{estudianteId}")
    public ResponseEntity<String> matricularEstudiante(@PathVariable("cursoId") Long cursoId, @PathVariable("estudianteId") Long estudianteId) {
        try {
            matriculaServicio.matricularEstudiante(cursoId, estudianteId);
            return ResponseEntity.ok("Estudiante matriculado exitosamente!!.");
        } catch (EstudianteMatriculadoExcepcion e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (TopeDeCursoExcepcion e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());// 403
        } catch (CursoNoEncontradoExcepcion | EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/curso/{cursoId}/desmatricular/estudiante/{estudianteId}")
    public ResponseEntity<String> desmatricularEstudiante(@PathVariable("cursoId") Long cursoId, @PathVariable("estudianteId") Long estudianteId) {
        try {
            matriculaServicio.desmatricularEstudiante(cursoId, estudianteId);
            return ResponseEntity.ok("Estudiante desmatriculado exitosamente!!.");
        } catch (CursoNoEncontradoExcepcion | EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public List<Matricula> obtenerMatriculas() {
        return matriculaServicio.obtenerMatriculas();
    }

    @DeleteMapping("/{cursoId}")
    public ResponseEntity<String> eliminarMatricula(@PathVariable("cursoId") Long cursoId) {
        try {
            matriculaServicio.eliminarMatricula(cursoId);
            return ResponseEntity.ok("Matricula eliminada exitosamente!!.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}