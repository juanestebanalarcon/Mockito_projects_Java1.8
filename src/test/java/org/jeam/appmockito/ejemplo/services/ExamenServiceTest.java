package org.jeam.appmockito.ejemplo.services;

import javafx.beans.binding.When;
import org.jeam.appmockito.ejemplo.models.Examen;
import org.jeam.appmockito.ejemplo.repositories.ExamenRepository;
import org.jeam.appmockito.ejemplo.repositories.IExamenRepository;
import org.jeam.appmockito.ejemplo.repositories.IPreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceTest {

        @Mock
        IExamenRepository repository;
        @Mock
        IPreguntaRepository preguntas;
        @InjectMocks
        IExamenService service;
        @Captor
        ArgumentCaptor<Long>capture;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        repository = mock(IExamenRepository.class);
//        preguntas = mock(IPreguntaRepository.class);
//        service = new ExamenService(repository,preguntas);

    }

    @Test
    void findExamendPorNombre() {
//        IExamenRepository repository = new ExamenRepository();
        //Simulando instancias
           //sólo métodos públicos
        when(repository.findAll()).thenReturn(Datos.DATOS);
        Examen examen = service.findExamendPorNombre("Sociales");
        assertNotNull(examen);
        assertEquals(5L,examen.getId());
        assertEquals("Sociales",examen.getNombre());

    }

    @Test
    void testPreguntasExamen() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
        when(preguntas.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen e = service.findExamendPorNombreConPreguntas("Sociales");
        assertEquals(4,e.getPreguntas().size());

    }
    @Test
    void testPreguntasExamenVerficar() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
        when(preguntas.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen e = service.findExamendPorNombreConPreguntas("Sociales");
        assertEquals(4,e.getPreguntas().size());
        verify(repository).findAll();
        verify(preguntas).findPreguntasPorExamenId(5L);
    }

    @Test
    void testGuardarExamen() {
        when(repository.save(any(Examen.class))).then(new Answer<Examen>(){
            Long secuencia = 8L;
            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
               Examen e = invocationOnMock.getArgument(0);
               e.setId(secuencia++);
                return e;
            }
        });
        Examen examen = service.save(Datos.EXAMEN);
        assertNotNull(examen.getId());
        assertEquals(8L,examen.getId());
        assertEquals("Física",examen.getNombre());
        verify(repository).save(any(Examen.class));
        verify(preguntas).save(anyList());

    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
//        When(preguntas.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    verify(preguntas).findPreguntasPorExamenId(argThat(arg->arg.equals(5L)));
    }

    @Test
    void testManejoException() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
        when(preguntas.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class,()->{
            service.findExamendPorNombre("Física");
        });
    }    @Test
    void testManejoExceptionDos() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_NEGATIVOS);
        when(preguntas.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class,()->{
            service.findExamendPorNombre("Física");
        });
        verify(preguntas.findPreguntasPorExamenId(argThat(new MyArgumentMatchers())));
    }
    public static class MyArgumentMatchers implements ArgumentMatcher<Long> {
        private Long argument;
        @Override
        public boolean matches(Long argument) {
            this.argument = arg;
            return argument != null && argument>0;
        }
        public String toString(){
            return "Mensaje personalizado de Mockito";
        }
    }

    @Test
    void testArgumentCapturer() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
        when(preguntas.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamendPorNombreConPreguntas("Sociales");
     //   ArgumentCaptor<Long>captor = ArgumentCaptor.forClass(Long.class);
        verify(preguntas).findPreguntasPorExamenId(capture.capture());
        assertEquals(5L,capture.getValue());

    }

    @Test
    void TestdoThrow() {
        Examen e = Datos.EXAMEN;
//        when(preguntas.save(anyList())).thenThrow(IllegalArgumentException.class);
         when(preguntas.findPreguntasPorExamenId(45L)).thenThrow(IllegalArgumentException.class);
         doThrow(IllegalArgumentException.class).when(preguntas).save(anyList());
         assertThrows(IllegalArgumentException.class,()->{
             service.save(e);

         });
    }

    @Test
    void testDoAnswer() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
//        when(preguntas.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doAnswer(invocation ->{
            Long id = invocation.getArgument(0);
            return id == 5? Datos.PREGUNTAS:null;
        }).when(preguntas).findPreguntasPorExamenId(anyLong());
        Examen e = service.findExamendPorNombreConPreguntas("Sociales");
        assertEquals(5L,e.getId());
        assertEquals("Sociales",e.getNombre());
        verify(preguntas).findPreguntasPorExamenId(anyLong());

    }

    @Test
    void testDoCallRealMethod() {
        when(repository.findAll()).thenReturn(Datos.DATOS);
//        when(preguntas.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doCallRealMethod().when(preguntas).findPreguntasPorExamenId(anyLong());
        Examen e = service.findExamendPorNombreConPreguntas("Sociales");
        assertEquals(5L,e.getId());
        assertEquals("Sociales",e.getNombre());

    }
}