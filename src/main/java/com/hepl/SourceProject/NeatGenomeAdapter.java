package com.hepl.SourceProject;

import com.hepl.NEAT.Genome;
import com.hepl.NEAT.GenomeWithFitness;

public class NeatGenomeAdapter implements Iindividual 
{
    GenomeWithFitness g;

    public NeatGenomeAdapter(GenomeWithFitness g)
    {
        this.g = g;
    }

    public Iindividual clone()
    {
        NeatGenomeAdapter clone = new NeatGenomeAdapter(this.g.clone());
		
		return clone;
    }

    @Override
    public int getFitness() {
        return (int)g.getFitness();
    }
        
}
