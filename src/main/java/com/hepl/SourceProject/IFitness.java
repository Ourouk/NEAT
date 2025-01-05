package com.hepl.SourceProject;

import com.hepl.NEAT.GenomeWithFitness;

public interface IFitness {
    public int getFitness(Individual individual);

    public int getFitness(GenomeWithFitness individual);
    public int getMaxFitness();
}
