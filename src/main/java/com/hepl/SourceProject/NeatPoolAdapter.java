package com.hepl.SourceProject;

import java.util.ArrayList;
import java.util.List;

import com.hepl.NEAT.Genome;
import com.hepl.NEAT.GenomeWithFitness;
import com.hepl.NEAT.Pool;
import com.hepl.NEAT.Species;

public class NeatPoolAdapter implements IPopulation
{
    private Pool pop;

    @Override
    public Individual getIndividual(int index) 
    {
        return getIndividuals().get(index);   
    }  

    @Override
    public List<Individual> getIndividuals() {
        
        List<GenomeWithFitness> gf = new ArrayList<>();
        for (Species species : pop.getSpecies()) 
        {
            for (GenomeWithFitness genome : species.getGenomes()) 
            {
                gf.add(genome);
            }
        }
        //Must add Individual adapter to transform genome into individual
        return null;

    }

    @Override
    public Individual getFittest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFittest'");
    }

    @Override
    public Individual crossover(Individual indiv1, Individual indiv2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crossover'");
    }

    @Override
    public void mutate(Individual indiv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mutate'");
    }

    

    
}
