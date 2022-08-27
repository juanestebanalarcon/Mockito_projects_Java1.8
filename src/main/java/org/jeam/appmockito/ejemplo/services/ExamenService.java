package org.jeam.appmockito.ejemplo.services;

import org.jeam.appmockito.ejemplo.models.Examen;
import org.jeam.appmockito.ejemplo.repositories.IExamenRepository;
import org.jeam.appmockito.ejemplo.repositories.IPreguntaRepository;

import java.util.List;
import java.util.Optional;

public class ExamenService implements IExamenService{
    private IExamenRepository repository;
    private IPreguntaRepository repoPreguntas;

    public ExamenService(IExamenRepository repository, IPreguntaRepository preguntaRepository) {
        this.repository = repository;
        this.repoPreguntas = preguntaRepository;
    }

    @Override
    public Examen findExamendPorNombre(String nombre) {
     Optional<Examen>examenList = repository.findAll().stream().filter(e ->e.getNombre().contains(nombre)).findFirst();
     Examen examen = null;
     if(examenList.isPresent()) {
         examen = examenList.get();
     }

     return examen;
    }

    @Override
    public Examen findExamendPorNombreConPreguntas(String nombre) {
        Examen examen = findExamendPorNombre(nombre);
        if(examen!=null) {
            List<String>preguntas = repoPreguntas.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }
}
