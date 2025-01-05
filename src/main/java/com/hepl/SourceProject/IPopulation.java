package com.hepl.SourceProject;

import java.util.List;

public interface IPopulation {


    Iindividual getIndividual(int index);

    List<Iindividual> getIndividuals(); 

    Iindividual getFittest();
    void add(Iindividual ind) throws Exception;
    IPopulation cloneEmpty();

    Iindividual crossover(Iindividual indiv1, Iindividual indiv2) throws Exception;

    void mutate(Iindividual indiv) throws Exception;
}
