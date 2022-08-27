package org.jeam.appmockito.ejemplo.repositories;

import org.jeam.appmockito.ejemplo.models.Examen;

import java.util.List;

public interface IExamenRepository {
    List<Examen>findAll();

}
