package com.hepl.NEAT;

import com.hepl.SourceProject.SimpleByteFitness;
import com.hepl.SourceProject.SimpleGeneticAlgorithm;

public class GenomeWithFitness { // doesn't modify the genome class -> based on hydrozoa YT videos
	private float fitness;
	private Genome genome;
	
	public GenomeWithFitness(Genome genome, float fitness) {
		this.genome = genome;
		this.fitness = fitness;
	}
	
	public float getFitness() {
		fitness = SimpleGeneticAlgorithm.FitnessManager.getFitness(this);
		return fitness;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	
	public Genome getGenome() {
		return genome;
	}
	public GenomeWithFitness clone()
	{
		return new GenomeWithFitness(genome.clone(), fitness);
	}
}
