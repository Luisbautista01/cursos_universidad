package com.Universidad.Cursos.Servicio;

import com.Universidad.Cursos.Excepciones.CursoNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.CursoSinProfesorExcepcion;
import com.Universidad.Cursos.Excepciones.ProfesorNoEncontradoExepcion;
import com.Universidad.Cursos.Modelo.Curso;
import com.Universidad.Cursos.Modelo.Profesor;
import com.Universidad.Cursos.Repositorio.CursoRepositorio;

import com.Universidad.Cursos.Repositorio.ProfesorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CursoServicio {

    private final CursoRepositorio cursoRepositorio;
    private final ProfesorRepositorio profesorRepositorio;

    @Autowired
    public CursoServicio(CursoRepositorio cursoRepositorio, ProfesorRepositorio profesorRepositorio) {
        this.cursoRepositorio = cursoRepositorio;
        this.profesorRepositorio = profesorRepositorio;
    }

    public void crearCurso(Curso curso) {
        if (curso.getProfesor() != null && curso.getProfesor().getId() != null) {
            Profesor profesor = profesorRepositorio.findById(curso.getProfesor().getId())
                    .orElseThrow(() -> new ProfesorNoEncontradoExepcion(curso.getId()));
            curso.setProfesor(profesor);
        }
        cursoRepositorio.save(curso);
    }

    public List<Curso> obtenerCursos() {
        return cursoRepositorio.findAll();
    }

    public Curso obtenerCursosPorId(Long cursoId) {
        return cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new CursoNoEncontradoExcepcion(cursoId));
    }

    public void eliminarCurso(Long cursoId) {
        Curso curso = cursoRepositorio.findById(cursoId)
                .orElseThrow(() -> new CursoNoEncontradoExcepcion(cursoId));
        cursoRepositorio.delete(curso);
    }

    public List<Curso> obtenerCursosPorEstudiante(Long estudianteId) {
        return cursoRepositorio.findByMatricula_Estudiante_Id(estudianteId);
    }

    public List<Curso> obtenerCursosPorProfesor(Profesor profesorId) {
        return cursoRepositorio.findCursosByProfesor(profesorId);
    }

    public int contarCursosPorProfesor(Long profesorId) {
        Profesor profesor = profesorRepositorio.findById(profesorId)
                .orElseThrow(CursoSinProfesorExcepcion::new);
        return cursoRepositorio.countByProfesor(profesor);
    }


    public List<Curso> obtenerCursoPorEstudianteYProfesor(Long estudianteId, Profesor profesorId) {
        return cursoRepositorio.findByMatricula_Estudiante_IdAndProfesor(estudianteId, profesorId);
    }

    public List<Curso> obtenerCursoPorEstudianteYMateria(Long estudianteId, String materia) {
        return cursoRepositorio.findByMatricula_Estudiante_IdAndMateria(estudianteId, materia);
    }
}


