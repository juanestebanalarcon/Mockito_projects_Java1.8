package org.jeam.appmockito.ejemplo.repositories;

import org.jeam.appmockito.ejemplo.Datos;

import java.util.List;

public class PreguntasRepository implements IPreguntaRepository{
    @Override
    public List<String> findPreguntasPorExamenId(Long Id) {
        return Datos.PREGUNTAS;
    }

    @Override
    public void save(List<String> preguntas) {

    }
}
