package com.Universidad.Cursos.Servicio;

import com.Universidad.Cursos.Excepciones.CursoNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.EstudianteMatriculadoExcepcion;
import com.Universidad.Cursos.Excepciones.EstudianteNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.TopeDeCursoExcepcion;
import com.Universidad.Cursos.Modelo.Curso;
import com.Universidad.Cursos.Modelo.Estudiante;
import com.Universidad.Cursos.Modelo.Matricula;
import com.Universidad.Cursos.Repositorio.CursoRepositorio;
import com.Universidad.Cursos.Repositorio.EstudianteRepositorio;
import com.Universidad.Cursos.Repositorio.MatriculaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MatriculaServicio {

    private final MatriculaRepositorio matriculaRepositorio;

    private final CursoRepositorio cursoRepositorio;

    private final EstudianteRepositorio estudianteRepositorio;

    @Autowired
    public MatriculaServicio(MatriculaRepositorio matriculaRepositorio, CursoRepositorio cursoRepositorio, EstudianteRepositorio estudianteRepositorio) {
        this.matriculaRepositorio = matriculaRepositorio;
        this.cursoRepositorio = cursoRepositorio;
        this.estudianteRepositorio = estudianteRepositorio;
    }

    //El registro de un curso lleva un seguimiento de cuándo un estudiante se
    // matricula y desmatricula de un curso.
    @Transactional
    public void matricularEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new CursoNoEncontradoExcepcion(cursoId));
        Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
                .orElseThrow(() -> new EstudianteNoEncontradoExcepcion(estudianteId));

        //Se Verifica si el estudiante ya está matriculado en el curso
        if (curso.getEstudiantes().contains(estudiante)) {
            throw new EstudianteMatriculadoExcepcion(estudianteId, cursoId);
        }

        //La inscripción a un curso no debería ser posible si el curso ya tiene el máximo de estudiantes
        // permitido.
        if (curso.cursoLlegoAlTope()) {
            throw new TopeDeCursoExcepcion(cursoId, estudianteId);
        }

        //Cuando un estudiante se inscribe a un curso, se debe crear un nuevo Matricula.
        Matricula matricula = new Matricula(curso, estudiante, LocalDate.now());
        matriculaRepositorio.save(matricula);

        curso.agregarMatricula(matricula);

        // Se Agrega el estudiante al curso y el curso al estudiante
        curso.getEstudiantes().add(estudiante);
        estudiante.getCursos().add(curso);

        // Se guardan los cambios en la base de datos
        cursoRepositorio.save(curso);
        estudianteRepositorio.save(estudiante);
    }

    //La desinscripción de un curso debe actualizar el Matricula correspondiente para incluir la
    // fecha de desmatriculación.
    public void desmatricularEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new CursoNoEncontradoExcepcion(cursoId));
        Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
                .orElseThrow(() -> new EstudianteNoEncontradoExcepcion(estudianteId));

        Matricula matricula = matriculaRepositorio.findByCursoAndEstudiante(curso, estudiante)
                .orElseThrow(() -> new RuntimeException("El curso o estudiante no se encuentran registrados."));

        // Se actualiza la fecha de desmatriculación
        matricula.setFechaDesmatricula(LocalDate.now());
        matriculaRepositorio.save(matricula);

        // se elimina la matrícula del curso y del estudiante
        curso.eliminarMatricula(matricula);
        estudiante.getMatricula().remove(matricula);
        estudiante.getCursos().remove(curso);

        // Se guardan los cambios en las entidades
        cursoRepositorio.save(curso);
        estudianteRepositorio.save(estudiante);
    }

    public List<Matricula> obtenerMatriculas() {
        return matriculaRepositorio.findAll();
    }

    public void eliminarMatricula(Long cursoId) {
        Matricula matricula = matriculaRepositorio.findById(cursoId).
                orElseThrow(() -> new RuntimeException("No se encontró la matrícula"));
        matriculaRepositorio.delete(matricula);

    }
}
