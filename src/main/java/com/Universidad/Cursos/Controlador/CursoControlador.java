package com.Universidad.Cursos.Controlador;

import com.Universidad.Cursos.Excepciones.CursoNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.CursoSinProfesorExcepcion;
import com.Universidad.Cursos.Excepciones.ProfesorNoEncontradoExepcion;
import com.Universidad.Cursos.Modelo.Curso;
import com.Universidad.Cursos.Modelo.Profesor;
import com.Universidad.Cursos.Servicio.CursoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoControlador {

    private final CursoServicio cursoServicio;

    @Autowired
    public CursoControlador(CursoServicio cursoServicio) {
        this.cursoServicio = cursoServicio;
    }

    @PostMapping()
    public ResponseEntity<String> crearCurso(@RequestBody Curso curso) {
        try {
            cursoServicio.crearCurso(curso);
            return ResponseEntity.ok("¡Se creo el curso correctamente!.");
        } catch (ProfesorNoEncontradoExepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    public List<Curso> obtenerCursos() {
        return cursoServicio.obtenerCursos();
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<Curso> obtenerCursoPorId(@PathVariable("cursoId") Long cursoId) {
        try {
            Curso curso = cursoServicio.obtenerCursosPorId(cursoId);
            return ResponseEntity.ok(curso);
        } catch (CursoNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{cursoId}")
    public ResponseEntity<String> eliminarCurso(@PathVariable("cursoId") Long cursoId) {
        try {
            cursoServicio.eliminarCurso(cursoId);
            return new ResponseEntity<>("¡Se elimino el curso correctamente!.", HttpStatus.OK);
        } catch (CursoNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public List<Curso> obtenerCursosPorEstudiante(@PathVariable("estudianteId") Long estudianteId) {
        return cursoServicio.obtenerCursosPorEstudiante(estudianteId);
    }

    @GetMapping("/profesor/{profesorId}")
    public List<Curso> obtenerCursosPorProfesor(@PathVariable("profesorId") Profesor profesorId) {
        return cursoServicio.obtenerCursosPorProfesor(profesorId);
    }


    @GetMapping("/profesor/{profesorId}/contar")
    public ResponseEntity<Integer> contarCursosPorProfesor(@PathVariable("profesorId") Long profesorId) {
        try {
            int contarCurso = cursoServicio.contarCursosPorProfesor(profesorId);
            return ResponseEntity.ok(contarCurso);
        } catch (CursoSinProfesorExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Al listar los cursos de un estudiante, muestra también el nombre del profesor para cada curso
    @GetMapping("/estudiante/{estudianteId}/profesor/{profesorId}")
    public List<Curso> obtenerCursoPorEstudianteYProfesor(@PathVariable("estudianteId") Long estudianteId, @PathVariable("profesorId") Profesor profesorId) {
        return cursoServicio.obtenerCursoPorEstudianteYProfesor(estudianteId, profesorId);
    }

    @GetMapping("/estudiante/{estudianteId}/materia/{materia}")
    public List<Curso> obtenerCursoPorEstudianteYMateria(@PathVariable("estudianteId") Long estudianteId, @PathVariable("materia") String materia) {
        return cursoServicio.obtenerCursoPorEstudianteYMateria(estudianteId, materia);
    }
}
