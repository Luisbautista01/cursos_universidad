package com.Universidad.Cursos;

import com.Universidad.Cursos.Excepciones.EstudianteNoEncontradoExcepcion;
import com.Universidad.Cursos.Excepciones.MenorDeEdadExcepcion;
import com.Universidad.Cursos.Modelo.Estudiante;
import com.Universidad.Cursos.Repositorio.CursoRepositorio;
import com.Universidad.Cursos.Repositorio.EstudianteRepositorio;
import com.Universidad.Cursos.Repositorio.ProfesorRepositorio;
import com.Universidad.Cursos.Servicio.EstudianteServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class EstudianteServicioTest {
    private EstudianteServicio estudianteServicio;
    private EstudianteRepositorio estudianteRepositorio;
    private CursoRepositorio cursoRepositorio;
    private ProfesorRepositorio profesorRepositorio;

    @BeforeEach
    public void setUp() {
        //mock-> sumulaciones
        this.estudianteRepositorio = mock(EstudianteRepositorio.class);
        this.cursoRepositorio = mock(CursoRepositorio.class);
        this.profesorRepositorio = mock(ProfesorRepositorio.class);
        this.estudianteServicio = new EstudianteServicio(estudianteRepositorio, cursoRepositorio, profesorRepositorio);
    }

    @Test
    public void crearEstudianteCuandoEsMayorDeEdad() {
        //Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setFechaNacimiento(LocalDate.parse("2004-12-05"));
        //Act
        estudianteServicio.crearEstudiante(estudiante);
        //Assert
        verify(estudianteRepositorio, times(1)).save(estudiante);
    }

    @Test
    public void crearEstudianteCuandoEsMenorDeEdad() {
        //Arrange
        Estudiante estudiante = new Estudiante();
        estudiante.setFechaNacimiento(LocalDate.parse("2010-04-12"));
        //Act & Assert
        assertThrows(MenorDeEdadExcepcion.class, () -> estudianteServicio.crearEstudiante(estudiante));
        verify(estudianteRepositorio, times(0)).save(estudiante); //never ó times(0): asegura de que el repositorio no sea  llamado.
    }

    @Test
    public void obtenereEstudiantes() {
        //Arrange
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombre("Luis");
        Estudiante estudiante2 = new Estudiante();
        estudiante2.setNombre("Antonio");

        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(estudiante1);
        estudiantes.add(estudiante2);

        when(estudianteRepositorio.findAll()).thenReturn(estudiantes);

        //Act
        List<Estudiante> resultado = estudianteServicio.obtenerEstudiantes();

        //Assert
        assertEquals(2, resultado.size(), "El tamaño de la lista debería ser 2");
        assertEquals("Luis", resultado.get(0).getNombre(), "El primer estudiante debería ser Luis");
        assertEquals("Antonio", resultado.get(1).getNombre(), "El segundo estudiante debería ser Antonio");
    }

    @Test
    public void obtenerEstudiantesPorIdNoExistente() {
        //Arrange
        Long id = 123L;
        when(estudianteRepositorio.findById(id)).thenReturn(Optional.empty());

        //Act-Assert
        assertThrows(EstudianteNoEncontradoExcepcion.class, () -> estudianteServicio.obtenerEstudiantesPorId(id));
        verify(estudianteRepositorio, times(1)).findById(id);
    }

    @Test
    public void actualizarEstudiante() {
        //Arrange
        Long id = 123L;
        Estudiante estudiante = new Estudiante();
        estudiante.setId(id);
        estudiante.setNombre("Luis");
        estudiante.setApellido("Bautista");

        Estudiante actualizar = new Estudiante();
        actualizar.setNombre("Pedro");
        actualizar.setApellido("Saenz");

        when(estudianteRepositorio.findById(id)).thenReturn(Optional.of(estudiante));
        when(estudianteRepositorio.save(any(Estudiante.class))).thenReturn(estudiante);

        //Act
        Estudiante resultado = estudianteServicio.actualizarEstudiante(id, actualizar);

        //Assert
        assertEquals("Pedro", resultado.getNombre());
        assertEquals("Saenz", resultado.getApellido());
        verify(estudianteRepositorio, times(1)).save(estudiante);
    }

    @Test
    public void eliminarEstudiante() {
        //Arrange
        Long id = 123L;
        Estudiante estudiante = new Estudiante();
        estudiante.setId(id);
        when(estudianteRepositorio.existsById(id)).thenReturn(true);

        //Act
        estudianteServicio.eliminarEstudiante(id);

        //Assert
        verify(estudianteRepositorio, times(1)).deleteById(id);
    }


    @Test
    public void eliminarEstudianteNoExistente() {
        // Arrange
        Long id = 123L;
        when(estudianteRepositorio.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EstudianteNoEncontradoExcepcion.class, () -> estudianteServicio.eliminarEstudiante(id));
        verify(estudianteRepositorio, never()).deleteById(id);
    }

    @Test
    public void obtenerCursosDelEstudiante() {
        //Arrange
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setId(1L);
        estudiante1.setNombre("Luis");

        List<Estudiante> estudiante = new ArrayList<>();
        estudiante.add(estudiante1);

    }
}
