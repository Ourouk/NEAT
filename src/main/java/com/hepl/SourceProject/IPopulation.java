package com.hepl.SourceProject;

import java.util.List;

public interface IPopulation {


    Individual getIndividual(int index);

    List<Individual> getIndividuals(); 

    Individual getFittest();

    Individual crossover(Individual indiv1, Individual indiv2);

     void mutate(Individual indiv);
        
}
