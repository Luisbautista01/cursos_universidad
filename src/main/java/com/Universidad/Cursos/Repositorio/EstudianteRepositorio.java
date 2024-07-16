package com.Universidad.Cursos.Repositorio;

import com.Universidad.Cursos.Modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepositorio extends JpaRepository<Estudiante, Long> {
}
