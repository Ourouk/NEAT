package com.hepl.SourceProject;

import com.hepl.NEAT.GenomeWithFitness;

public class SimpleByteFitness implements IFitness {

    private byte[] solution; //Solution may be dynamic in it's size (https://ai.stackexchange.com/questions/8751/how-to-use-genetic-algorithm-for-varying-lengths-of-solutions#:~:text=A%20GP%20replaces%20the%20fixed%20length%20vector%20structure)

    public SimpleByteFitness(String newSolution)
    {
        setSolution(newSolution);
    }

    @Override
    public int getFitness(Individual individual) {
        int fitness = 0;
		for (int i = 0; i < individual.getGeneLength() && i < solution.length; i++) {
			if (individual.getSingleGene(i) == solution[i]) {
				fitness++;
			}
		}

		//Penality for added bits -> Sans pénalité, du simple au double !? Contre productif dans ce cas ?
		//fitness=fitness-Math.abs(individual.getGeneLength()-solution.length)/2; // ! si /2 -> OVERSHOOT boom ?!?
		fitness -= Math.abs(individual.getGeneLength() - solution.length);


		return Math.max(fitness+128,1);
    }

    @Override
    public int getMaxFitness() {
        int maxFitness = solution.length+128;
        return maxFitness;
    }
    
    public void setSolution(String newSolution) {
		solution = new byte[newSolution.length()];

		for (int i = 0; i < newSolution.length(); i++) {
			String character = newSolution.substring(i, i + 1);
			if (character.equals("0") || character.equals("1")) {
				solution[i] = Byte.parseByte(character);
			} else {
				solution[i] = 0;
			}
		}
	}

	@Override
	public int getFitness(GenomeWithFitness individual) {
		float fitness = 0;
		for (int i = 0; i < solution.length; i++) 
		{
			String strInput =Integer.toBinaryString(i);
			float[] in = new float[strInput.length()];
			for (int j = 0; j < strInput.length();j++) 
			{
				in[j] = (float)Integer.parseInt(String.valueOf(strInput.charAt(j)));
			}
			fitness += 1/((Math.abs(solution[i] - individual.getGenome().getOutputs(in)[0]))+1);
		}
		//Set the max fitness to solution.length+128
		fitness *= (solution.length+128.0)/(solution.length);
		return (int )fitness;
	}
}
