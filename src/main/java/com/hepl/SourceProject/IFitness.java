package com.hepl.SourceProject;

public interface IFitness {
    public int getFitness(Individual individual);

    public int getFitness(NeatGenomeAdapter individual);
    public int getMaxFitness();
}
