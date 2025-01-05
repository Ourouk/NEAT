package com.hepl.SourceProject;

import com.hepl.NEAT.AppConfig;
import com.hepl.NEAT.GenomeWithFitness;

public class GameFitness implements IFitness {

    private Game solution;
    public GameFitness(Game g)
    {
        solution = g;
    }
    @Override
    public int getFitness(Individual individual)
    {
        float score = solution.PlayStatic(individual.getGenes());
        System.out.println(score);
        int fitness = Math.round(1000f/(score));

		return Math.max(fitness,1);
    }


    @Override
    public int getMaxFitness() {
        return 999;
    }
    @Override
    public int getFitness(GenomeWithFitness individual) {
        int MinVal = Integer.MAX_VALUE;
        for (int i = 0; i < AppConfig.NEAT_MAX_GAME_STEPS; i++) 
        {
            solution.Move(individual.getGenome().getOutputs(solution.getSurounding()));
            if(MinVal > solution.getPlayerValue())
            {
                MinVal = solution.getPlayerValue();
            }  
        }
        solution.Reset();
        int fitness = Math.round(1000f/(MinVal));

		return Math.max(fitness,1);
    }
}