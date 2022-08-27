package org.jeam.appmockito.ejemplo.services;

import org.jeam.appmockito.ejemplo.models.Examen;

public interface IExamenService {
    Examen findExamendPorNombre(String nombre);
    Examen findExamendPorNombreConPreguntas(String nombre);


}
