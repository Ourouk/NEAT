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
    public Iindividual getIndividual(int index) 
    {
        return getIndividuals().get(index);   
    }  

    @Override
    public List<Iindividual> getIndividuals() {
        
        List<Iindividual> ret = new ArrayList<>();
        for (Species species : pop.getSpecies()) 
        {
            for (GenomeWithFitness genome : species.getGenomes()) 
            {
                ret.add(new NeatGenomeAdapter(genome));
            }
        }

        //Must add Individual adapter to transform genome into individual
        return ret;

    }

    @Override
    public Iindividual getFittest() 
    {
        GenomeWithFitness fittest = null;
        int maxFitness = -1;
        List<Iindividual> ret = new ArrayList<>();
        for (Species species : pop.getSpecies()) 
        {
            for (GenomeWithFitness genome : species.getGenomes()) 
            {
                if(maxFitness <= genome.getFitness())
                {
                    maxFitness = (int)genome.getFitness();
                    fittest = genome;
                }
            }
        }
        return new NeatGenomeAdapter(fittest);
    }

    @Override
    public Iindividual crossover(Iindividual Iindiv1, Iindividual Iindiv2) throws Exception 
    {
        if(!(Iindiv1 instanceof NeatGenomeAdapter && Iindiv2 instanceof NeatGenomeAdapter))
        {
            throw new Exception("This NEAT population received incorrect individuals");
        }
        NeatGenomeAdapter indiv1 = (NeatGenomeAdapter)Iindiv1;
        NeatGenomeAdapter indiv2 = (NeatGenomeAdapter)Iindiv2;
        
        Genome g = pop.GenomeCrossover(indiv1.g.getGenome(), indiv2.g.getGenome(), indiv1.g.getFitness(), indiv2.g.getFitness());
        return new NeatGenomeAdapter(new GenomeWithFitness(g, 0));
    }

    @Override
    public void mutate(Iindividual Iindiv) throws Exception  
    {
        if(!(Iindiv instanceof Individual))
        {
            throw new Exception("This NEAT population received incorrect individuals");
        }
        NeatGenomeAdapter indiv = (NeatGenomeAdapter)Iindiv;
        throw new UnsupportedOperationException("Pool hasn't any definition for group mutation please implement before continueing");

    }

    

    
}