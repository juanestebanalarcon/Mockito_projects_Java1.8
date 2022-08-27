package org.jeam.appmockito.ejemplo.services;

import org.jeam.appmockito.ejemplo.models.Examen;
import org.jeam.appmockito.ejemplo.repositories.ExamenRepository;
import org.jeam.appmockito.ejemplo.repositories.IExamenRepository;
import org.jeam.appmockito.ejemplo.repositories.IPreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceTest {

        IExamenRepository repository;
        IExamenService service;
        IPreguntaRepository preguntas;
    @BeforeEach
    void setUp() {
        repository = mock(IExamenRepository.class);
        preguntas = mock(IPreguntaRepository.class);
        service = new ExamenService(repository,preguntas);

    }

    @Test
    void findExamendPorNombre() {
//        IExamenRepository repository = new ExamenRepository();
        //Simulando instancias
        List<Examen>data = Arrays.asList(new Examen(5L,"Sociales"),new Examen(6L,"Ingles"),new Examen(7L,"Español"));
        //sólo métodos públicos
        when(repository.findAll()).thenReturn(data);
        Examen examen = service.findExamendPorNombre("Sociales");
        assertNotNull(examen);
        assertEquals(5L,examen.getId());
        assertEquals("Sociales",examen.getNombre());

    }
}