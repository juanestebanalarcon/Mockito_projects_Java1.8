package org.jeam.appmockito.ejemplo.services;

import org.jeam.appmockito.ejemplo.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
   public final static List<Examen> DATOS = Arrays.asList(new Examen(5L,"Sociales"),new Examen(6L,"Ingles"),new Examen(7L,"Español"));
   public final static List<Examen> EXAMENES_NEGATIVOS = Arrays.asList(new Examen(-5L,"Sociales"),new Examen(-6L,"Ingles"),new Examen(-7L,"Español"));
   public final static List<String> PREGUNTAS = Arrays.asList("Sociales,""Aritmética","Geometría");
   public final static Examen EXAMEN =new Examen(8L,"Física");

}
