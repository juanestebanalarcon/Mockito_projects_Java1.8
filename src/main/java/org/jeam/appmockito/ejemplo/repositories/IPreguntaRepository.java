package org.jeam.appmockito.ejemplo.repositories;

import java.util.List;

public interface IPreguntaRepository {
    List<String>findPreguntasPorExamenId(Long Id);
    void save(List<String>preguntas);
}
