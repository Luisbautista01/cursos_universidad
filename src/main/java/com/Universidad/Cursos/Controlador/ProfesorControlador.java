package com.Universidad.Cursos.Controlador;

import com.Universidad.Cursos.DTOs.ProfesorDTO;
import com.Universidad.Cursos.Excepciones.ProfesorConCursosAsociadosExepcion;
import com.Universidad.Cursos.Excepciones.ProfesorNoEncontradoExepcion;
import com.Universidad.Cursos.Excepciones.TitulosAcademicosExepcion;
import com.Universidad.Cursos.Modelo.Profesor;
import com.Universidad.Cursos.Servicio.ProfesorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorControlador {

    private final ProfesorServicio profesorServicio;

    @Autowired
    public ProfesorControlador(ProfesorServicio profesorServicio) {
        this.profesorServicio = profesorServicio;
    }

    @PostMapping()
    public ResponseEntity<String> crearProfesor(@RequestBody Profesor profesor) {
        try {
            profesorServicio.crearProfesor(profesor);
            return ResponseEntity.ok("¡Se creo el profesor correctamente!.");
        } catch (TitulosAcademicosExepcion e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    public List<Profesor> obtenerProfesores() {
        return profesorServicio.obtenerProfesores();
    }

    @GetMapping("/{profesorId}")
    public ResponseEntity<Profesor> obtenerProfesoresPorId(@PathVariable("profesorId") Long profesorId) {
        try {
            Profesor profesorObtenido = profesorServicio.obtenerProfesoresPorId(profesorId);
            return ResponseEntity.ok(profesorObtenido);
        } catch (ProfesorNoEncontradoExepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{profesorId}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable("profesorId") Long profesorId, @RequestBody Profesor actualizar) {
        try {
            Profesor profesorActualizado = profesorServicio.actualizarProfesor(profesorId, actualizar);
            return ResponseEntity.ok(profesorActualizado);
        } catch (ProfesorNoEncontradoExepcion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{profesorId}")
    public ResponseEntity<String> eliminarProfesor(@PathVariable("profesorId") Long profesorId) {
        try {
            profesorServicio.eliminarProfesor(profesorId);
            return ResponseEntity.ok("Profesor eliminado exitosamente!!.");
        } catch (ProfesorConCursosAsociadosExepcion e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


    //Extras:
    @GetMapping("/{id}/cursos")
    public List<ProfesorDTO> obtenerProfesoresConCantidadDeCursos(@PathVariable("id") Long id) {
        return profesorServicio.obtenerProfesoresConCantidadDeCursos();
    }

}
