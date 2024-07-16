package com.Universidad.Cursos.Repositorio;

import com.Universidad.Cursos.Modelo.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepositorio extends JpaRepository<Profesor, Long> {
    Profesor findByCursosId(Long cursoId);
}
