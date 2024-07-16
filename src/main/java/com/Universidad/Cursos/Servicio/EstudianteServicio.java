package com.Universidad.Cursos.Servicio;

import com.Universidad.Cursos.DTOs.CursoDTO;
import com.Universidad.Cursos.DTOs.Curso_SemestreDTO;
import com.Universidad.Cursos.Excepciones.CursosYSemestresNoEncontradosExcepcion;
import com.Universidad.Cursos.Excepciones.EstudianteNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.MenorDeEdadExcepcion;
import com.Universidad.Cursos.Modelo.Curso;
import com.Universidad.Cursos.Modelo.Estudiante;
import com.Universidad.Cursos.Modelo.Profesor;
import com.Universidad.Cursos.Repositorio.CursoRepositorio;
import com.Universidad.Cursos.Repositorio.EstudianteRepositorio;
import com.Universidad.Cursos.Repositorio.ProfesorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstudianteServicio {

    private final EstudianteRepositorio estudianteRepositorio;
    private final CursoRepositorio cursoRepositorio;
    private final ProfesorRepositorio profesorRepositorio;

    @Autowired
    public EstudianteServicio(EstudianteRepositorio estudianteRepositorio, CursoRepositorio cursoRepositorio, ProfesorRepositorio profesorRepositorio) {
        this.estudianteRepositorio = estudianteRepositorio;
        this.cursoRepositorio = cursoRepositorio;
        this.profesorRepositorio = profesorRepositorio;
    }

    @Transactional
    //@Transactional: Todas las operaciones se completarán con éxito o se revertirán en caso de error, asegurando la consistencia de los datos.
    public void crearEstudiante(Estudiante estudiante) {
        if (estudiante.esMayorDeEdad()) { //Validamos que los estudiantes sean mayores de 18 años
            estudianteRepositorio.save(estudiante);
        } else {
            throw new MenorDeEdadExcepcion(estudiante);
        }
    }

    @Transactional(readOnly = true)
    //@Transactional(readOnly = true) para métodos que solo leen datos de la base de datos:
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteRepositorio.findAll();
    }

    // Se obtiene la id del estudinate y este método se implementa en los metodos:
    // obtenerCursosDelEstudiante y cursosEnSemestre: EstudianteControlador
    @Transactional(readOnly = true)
    public Estudiante obtenerEstudiantesPorId(Long id) {
        return estudianteRepositorio.findById(id).orElseThrow(() -> new EstudianteNoEncontradoExcepcion(id));
    }

    @Transactional
    public Estudiante actualizarEstudiante(Long id, Estudiante actualizar) {
        Estudiante estudiante = estudianteRepositorio.findById(id)
                .orElseThrow(() -> new EstudianteNoEncontradoExcepcion(id));
        estudiante.setNombre(actualizar.getNombre());
        estudiante.setApellido(actualizar.getApellido());

        return estudianteRepositorio.save(estudiante);
    }

    @Transactional
    public void eliminarEstudiante(Long id) {
        Optional<Estudiante> estudianteEliminar = estudianteRepositorio.findById(id);
        if (estudianteRepositorio.existsById(id)) {
            estudianteRepositorio.deleteById(id);
        } else {
            throw new EstudianteNoEncontradoExcepcion(id);
        }
    }


    //Extras:
    // Al listar los cursos de un estudiante, muestra también el nombre del profesor para cada curso.
    @Transactional(readOnly = true)
    public List<CursoDTO> obtenerCursosDelEstudiante(Long id) {
        List<Curso> cursos = cursoRepositorio.findByEstudiantes_Id(id);
        return cursos.stream().map(curso -> {
            Profesor profesor = profesorRepositorio.findByCursosId(curso.getId());
            return new CursoDTO(curso.getId(), curso.getNombre(), curso.getDescripcion(), curso.getMateria(), profesor.getNombre());
        }).collect(Collectors.toList());
    }

    //Se implementa un endpoint que muestre todos los cursos en los que un estudiante está
    //matriculado en un semestre específico
    @Transactional(readOnly = true)
    public Curso_SemestreDTO obtenerCursosConSemestre(Long id, String semestre) {
        if (!estudianteRepositorio.existsById(id)) {
            throw new EstudianteNoEncontradoExcepcion(id);
        }

        List<Curso> cursos = cursoRepositorio.findByEstudiantes_IdAndSemestre(id, semestre);

        if (cursos.isEmpty()) {
            throw new CursosYSemestresNoEncontradosExcepcion(id, semestre);
        }

        List<CursoDTO> cursoDTOs = cursos.stream().map(curso -> {
            Profesor profesor = profesorRepositorio.findByCursosId(curso.getId());
            return new CursoDTO(curso.getId(), curso.getNombre(), curso.getDescripcion(), curso.getMateria(), profesor.getNombre());
        }).collect(Collectors.toList());

        return new Curso_SemestreDTO(semestre, cursoDTOs);
    }

}
