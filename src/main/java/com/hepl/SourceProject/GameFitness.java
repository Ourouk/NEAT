package com.hepl.SourceProject;

public class GameFitness implements IFitness {

    private Game solution;
    public GameFitness(Game g)
    {
        solution = g;
    }
    @Override
    public int getFitness(Individual individual) {
        float score = solution.PlayStatic(individual.getGenes());
        System.out.println(score);
        int fitness = Math.round(1000f/(score));

		return Math.max(fitness,1);
    }

    @Override
    public int getMaxFitness() {
        return 999;
    }
    
}
