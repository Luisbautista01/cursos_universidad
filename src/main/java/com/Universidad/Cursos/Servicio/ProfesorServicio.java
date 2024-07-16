package com.Universidad.Cursos.Servicio;

import com.Universidad.Cursos.DTOs.ProfesorDTO;
import com.Universidad.Cursos.Excepciones.ProfesorConCursosAsociadosExepcion;
import com.Universidad.Cursos.Excepciones.ProfesorNoEncontradoExepcion;
import com.Universidad.Cursos.Excepciones.TitulosAcademicosExepcion;
import com.Universidad.Cursos.Modelo.Profesor;
import com.Universidad.Cursos.Repositorio.CursoRepositorio;
import com.Universidad.Cursos.Repositorio.ProfesorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfesorServicio {

    private final ProfesorRepositorio profesorRepositorio;

    private final CursoRepositorio cursoRepositorio;

    @Autowired
    public ProfesorServicio(ProfesorRepositorio profesorRepositorio, CursoRepositorio cursoRepositorio) {
        this.profesorRepositorio = profesorRepositorio;
        this.cursoRepositorio = cursoRepositorio;
    }

    public void crearProfesor(Profesor profesor) {
        if (profesor.getTitulosAcademicos() == null) {
            throw new TitulosAcademicosExepcion(profesor);
        } else {
            profesorRepositorio.save(profesor);
        }
    }

    public List<Profesor> obtenerProfesores() {
        return profesorRepositorio.findAll();
    }

    public Profesor obtenerProfesoresPorId(Long profesorId) {
        return profesorRepositorio.findById(profesorId).
                orElseThrow(() -> new ProfesorNoEncontradoExepcion(profesorId));
    }

    public Profesor actualizarProfesor(Long profesorId, Profesor actualizar) {
        Profesor profesor = profesorRepositorio.findById(profesorId)
                .orElseThrow(() -> new ProfesorNoEncontradoExepcion(profesorId));
        profesor.setNombre(actualizar.getNombre());
        profesor.setApellido(actualizar.getApellido());
        profesor.setTitulosAcademicos(actualizar.getTitulosAcademicos());

        return profesorRepositorio.save(profesor);
    }

    //Un profesor no debe poder ser eliminado si tiene cursos asociados
    public void eliminarProfesor(Long profesorId) {
        profesorRepositorio.findById(profesorId)
                .orElseThrow(() -> new ProfesorNoEncontradoExepcion(profesorId));
        if (cursoRepositorio.existsByProfesorId(profesorId)) {
            throw new ProfesorConCursosAsociadosExepcion(profesorId);
        } else {
            profesorRepositorio.deleteById(profesorId);
        }
    }


    //Extra:
    //Al listar los profesores, muestra también la cantidad de cursos que está dando cada uno
    public List<ProfesorDTO> obtenerProfesoresConCantidadDeCursos() {
        List<Profesor> profesores = profesorRepositorio.findAll();

        return profesores.stream().map(profesor -> {
            ProfesorDTO profesorDTO = new ProfesorDTO(
                    profesor.getId(),
                    profesor.getNombre(),
                    profesor.getApellido(),
                    profesor.getTitulosAcademicos(),
                    profesor.getFechaInicioTrabajo()
            );
            //Calculamos la cantidad de cursos
            profesorDTO.setCantidadDeCursos(profesor.getCursos().size());
            return profesorDTO;
        }).collect(Collectors.toList());
    }


    /*Sin profesorDTO:
    public List<Profesor> obtenerProfesoresConCantidadDeCursos() {
        List<Profesor> profesores = profesorRepositorio.findAll();

        profesores.forEach(profesor -> profesor.setCantidadDeCursos(profesor.getCursos().size()));

        return profesores;
    }*/
}

