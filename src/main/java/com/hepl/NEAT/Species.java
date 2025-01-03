package com.hepl.NEAT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Species {
	// A species -> List of genomes where the compatibility distance is less than a certain threshold
	private GenomeWithFitness representativeGenome; // represent all the species
	private List<GenomeWithFitness> genomes = new ArrayList<GenomeWithFitness>(); // individuals
	
	private int stagnationCounter;
	
	private float bestFitnessInside;
	private float averageFitness;
	
	private static Random rand = new Random();
	
	public Species(GenomeWithFitness representativeGenome) {
		this.representativeGenome = representativeGenome;
		this.genomes.add(representativeGenome);
		this.stagnationCounter = 0;
	}
	
	/*
	 * GETTERS
	 */
	public float getAverageFitness() {
		return averageFitness;
	}
		
	public GenomeWithFitness getRepresentativeGenome() {
		return representativeGenome;
	}
	
	public int getStagnationCounter() {
		return stagnationCounter;
	}
	
	public List<GenomeWithFitness> getGenomes(){
		return genomes;
	}
	
	// Best Fitness in the species
	public float getBestFitnessInside() {
		this.sortGenomesByFitness();
		bestFitnessInside = genomes.get(0).getFitness();
		return bestFitnessInside;
	}

	/*
	 * METHODS
	 */
	public void addGenome(GenomeWithFitness g) {
		genomes.add(g);
	}
	
	public void setRepresentativeGenome(GenomeWithFitness representativeGenome) {
		this.representativeGenome = representativeGenome;
	}

	// Average fitness = (all the fitness of all the genomes) / (#genomes in the species)
	public void calculateAverageFitness() {
		float totalFitness = 0;
		
		for (GenomeWithFitness genome : genomes) {
			totalFitness += genome.getFitness();
		}
		this.averageFitness = totalFitness / genomes.size();
	}
	
	// Adjust fitness = (fitness of each genome) / (#genomes in the species)
	public void adjustFitness() {
		for (GenomeWithFitness genome : genomes) {
			genome.setFitness(genome.getFitness() / genomes.size());
		}
	}
	
	// Contains the best genome ? With the best fitness in all the population
	public boolean haveFittestGenome(float bestFitnessInPopulation) {
		for (GenomeWithFitness genome : genomes) {
			if (genome.getFitness() == bestFitnessInPopulation) {
				return true;
			}
		}
		return false;
	}
	
	// History of a species, to be incremented for each epoch (generation)
	public void incrementStagnationCounter() {
		stagnationCounter++;
	}
	
	// Sort genomes by their fitness
	public void sortGenomesByFitness() { // https://stackoverflow.com/questions/12542185/sort-a-java-collection-object-based-on-one-field-in-it
		genomes.sort(Comparator.comparing(GenomeWithFitness::getFitness).reversed());
	}
	
	// Remove old and weak genomes
	public void removeOldWeakGenome() {
		float percentageToKeep = 0.5f; // if 50% -> divide by 2, if 0%, keep the best
		this.sortGenomesByFitness();
		int indexToKeep = Math.max(1, (int)(genomes.size() * percentageToKeep));
		
		for (int i = indexToKeep; i < genomes.size(); i++) {
			genomes.remove(i);
		}
	}
	
	// Select a random genome used to be the next representative in the next generation
	public GenomeWithFitness selectRandomGenome() {
		return genomes.get(rand.nextInt(genomes.size()));
	}
	
	public void reset() {
		genomes.clear();
		this.stagnationCounter = 0;
	}
}
