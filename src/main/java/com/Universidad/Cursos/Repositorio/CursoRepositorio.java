package com.Universidad.Cursos.Repositorio;

import com.Universidad.Cursos.Modelo.Curso;
import com.Universidad.Cursos.Modelo.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepositorio extends JpaRepository<Curso, Long> {
    // Verficamos si existen cursos asociados a un profesor por su ID
    boolean existsByProfesorId(Long profesorId);

    List<Curso> findCursosByProfesor(Profesor profesorId);

    int countByProfesor(Profesor profesor);

    List<Curso> findByMatricula_Estudiante_Id(Long estudianteId);

    List<Curso> findByMatricula_Estudiante_IdAndProfesor(Long estudianteId, Profesor profesorId);

    List<Curso> findByMatricula_Estudiante_IdAndMateria(Long estudianteId, String materia);

    List<Curso> findByEstudiantes_IdAndSemestre(Long estudianteId, String semestre);

    List<Curso> findByEstudiantes_Id(Long estudianteId);
}

