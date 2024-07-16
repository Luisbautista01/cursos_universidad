package com.Universidad.Cursos.Repositorio;

import com.Universidad.Cursos.Modelo.Curso;
import com.Universidad.Cursos.Modelo.Estudiante;
import com.Universidad.Cursos.Modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatriculaRepositorio extends JpaRepository<Matricula, Long> {
    Optional<Matricula> findByCursoAndEstudiante(Curso curso, Estudiante estudiante);
}
