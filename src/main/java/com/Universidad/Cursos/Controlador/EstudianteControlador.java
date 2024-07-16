package com.Universidad.Cursos.Controlador;

import com.Universidad.Cursos.DTOs.CursoDTO;
import com.Universidad.Cursos.DTOs.Curso_SemestreDTO;
import com.Universidad.Cursos.Excepciones.CursosYSemestresNoEncontradosExcepcion;
import com.Universidad.Cursos.Excepciones.EstudianteNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.MenorDeEdadExcepcion;
import com.Universidad.Cursos.Modelo.Estudiante;
import com.Universidad.Cursos.Servicio.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudianteControlador {

    private final EstudianteServicio estudianteServicio;

    @Autowired
    public EstudianteControlador(EstudianteServicio estudianteServicio) {
        this.estudianteServicio = estudianteServicio;
    }

    // Se puede eliminar el manejo de excepciones específicas, ya que el manejo global se encargará de eso.
    @PostMapping()
    public ResponseEntity<String> crearEstudiante(@RequestBody Estudiante estudiante) {
        try {
            estudianteServicio.crearEstudiante(estudiante);
            return ResponseEntity.status((HttpStatus.CREATED)).body("¡Se creo el estudiante con éxito!."); //400 Bad Request
        } catch (MenorDeEdadExcepcion e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); //400 Bad Request
        }
    }

    @GetMapping()
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteServicio.obtenerEstudiantes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiantesPorId(@PathVariable("id") Long id) {
        try {
            Estudiante estudianteActualizado = estudianteServicio.obtenerEstudiantesPorId(id);
            return ResponseEntity.ok(estudianteActualizado);
        } catch (EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(@PathVariable("id") Long id, @RequestBody Estudiante actualizar) {
        try {
            Estudiante estudianteActualizado = estudianteServicio.actualizarEstudiante(id, actualizar);
            return ResponseEntity.ok(estudianteActualizado);
        } catch (EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable("id") Long id) {
        try {
            estudianteServicio.eliminarEstudiante(id);
            return new ResponseEntity<>("Se elimino el estudiante correctamente!!.", HttpStatus.OK);
        } catch (EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Extras:
    @GetMapping("/{id}/cursos")
    public ResponseEntity<List<CursoDTO>> obtenerCursosDeEstudianteConProfesor(@PathVariable("id") Long id) {
        try {
            List<CursoDTO> cursos = estudianteServicio.obtenerCursosDelEstudiante(id);
            return ResponseEntity.ok(cursos);
        } catch (EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}/cursos/{semestre}")
    public ResponseEntity<Object> obtenerCursosConSemestre(@PathVariable("id") Long id, @PathVariable("semestre") String semestre) {
        try {
            Curso_SemestreDTO cursoSemestreDTO = estudianteServicio.obtenerCursosConSemestre(id, semestre);
            return ResponseEntity.ok(cursoSemestreDTO); //200
        } catch (CursosYSemestresNoEncontradosExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); //404
        } catch (EstudianteNoEncontradoExcepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Devolvemos un mensaje de error general
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado."); // 500 Internal Server Error
        }
    }

}
