package org.jeam.appmockito.ejemplo.repositories;

import org.jeam.appmockito.ejemplo.models.Examen;

import java.util.Arrays;
import java.util.List;

public class ExamenRepository implements IExamenRepository{
    @Override
    public List<Examen> findAll() {
        return Arrays.asList(new Examen(5L,"Sociales"),new Examen(6L,"Ingles"),new Examen(7L,"Español"));
    }
}
